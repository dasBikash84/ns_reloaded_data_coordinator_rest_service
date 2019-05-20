package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.exceptions.IllegalRequestBodyException
import com.dasbikash.news_server_data_coordinator_rest.model.ArticleUploaderStatusChangeLogs
import com.dasbikash.news_server_data_coordinator_rest.model.ArticleUploaderStatusChangeRequest
import com.dasbikash.news_server_data_coordinator_rest.model.ArticleUploaderStatusChangeRequestFormat
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.ArticleUploaderStatusChangeLog
import com.dasbikash.news_server_data_coordinator_rest.services.ArticleUploaderStatusChangeLogService
import com.dasbikash.news_server_data_coordinator_rest.services.AuthTokenService
import com.dasbikash.news_server_data_coordinator_rest.utills.EmailUtils
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("article-uploader-status-change-logs",
        produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class ArticleUploaderStatusChangeLogController
constructor(open var articleUploaderStatusChangeLogService: ArticleUploaderStatusChangeLogService,
            open var authTokenService: AuthTokenService,
            open var restControllerUtills: RestControllerUtills) {

    @Value("\${log.default_page_size}")
    open var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    open var maxPageSize: Int = 50

    @GetMapping("",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getLatestArticleUploaderStatusChangeLogsEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                              @Autowired request: HttpServletRequest)
            : ResponseEntity<ArticleUploaderStatusChangeLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(
                ArticleUploaderStatusChangeLogs(articleUploaderStatusChangeLogService.getLatestArticleUploaderStatusChangeLogs(pageSize)))
    }

    @GetMapping("/before/{log-id}",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getArticleUploaderStatusChangeLogsBeforeGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                                    @PathVariable("log-id") lastStatusChangeLogId:Int,
                                                                     @Autowired request: HttpServletRequest)
            : ResponseEntity<ArticleUploaderStatusChangeLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(ArticleUploaderStatusChangeLogs(
                articleUploaderStatusChangeLogService.getArticleUploaderStatusChangeLogsBeforeGivenId(lastStatusChangeLogId,pageSize)))
    }

    @GetMapping("/after/{log-id}",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getArticleUploaderStatusChangeLogsAfterGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                                    @PathVariable("log-id") lastStatusChangeLogId:Int,
                                                                    @Autowired request: HttpServletRequest)
            : ResponseEntity<ArticleUploaderStatusChangeLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(ArticleUploaderStatusChangeLogs(
                articleUploaderStatusChangeLogService.getArticleUploaderStatusChangeLogsAfterGivenId(lastStatusChangeLogId,pageSize)))
    }

    @GetMapping("request_status_change_token_generation",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun generateStatusChangeTokenEndPoint(@Autowired request: HttpServletRequest)
            :ResponseEntity<ArticleUploaderStatusChangeRequestFormat>{
        val newToken = authTokenService.getNewAuthToken()
        EmailUtils.emailAuthTokenToAdmin(newToken,this::class.java)
        return restControllerUtills.entityToResponseEntity(ArticleUploaderStatusChangeRequestFormat())
    }

    @PostMapping("status_change_request",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun changeUploaderStatusEndPoint(@RequestBody articleUploaderStatusChangeRequest: ArticleUploaderStatusChangeRequest?,
                                          @Autowired request: HttpServletRequest)
            :ResponseEntity<ArticleUploaderStatusChangeRequest>{
        if (articleUploaderStatusChangeRequest == null ||
                articleUploaderStatusChangeRequest.authToken == null ||
                articleUploaderStatusChangeRequest.articleUploadTarget == null ||
                articleUploaderStatusChangeRequest.status == null ){
            throw IllegalRequestBodyException()
        }
        authTokenService.invalidateAuthToken(articleUploaderStatusChangeRequest.authToken)
        try {
            val statusData = ArticleUploaderStatusChangeLog(
                    status = articleUploaderStatusChangeRequest.status,
                    articleDataUploaderTarget = articleUploaderStatusChangeRequest.articleUploadTarget)
            articleUploaderStatusChangeLogService.save(statusData)
            return restControllerUtills.entityToResponseEntity(articleUploaderStatusChangeRequest)
        }catch (ex:Exception){
            throw InternalError()
        }
    }
}
