package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.exceptions.DataNotFoundException
import com.dasbikash.news_server_data_coordinator_rest.exceptions.IllegalRequestBodyException
import com.dasbikash.news_server_data_coordinator_rest.model.ArticleUploaderStatusChangeRequest
import com.dasbikash.news_server_data_coordinator_rest.model.ArticleUploaderStatusChangeRequestFormat
import com.dasbikash.news_server_data_coordinator_rest.model.database.AuthToken
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.ArticleUploaderStatusChangeLog
import com.dasbikash.news_server_data_coordinator_rest.services.ArticleUploaderStatusChangeLogService
import com.dasbikash.news_server_data_coordinator_rest.services.AuthTokenService
import com.dasbikash.news_server_data_coordinator_rest.utills.EmailUtils
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("article-uploader-status-change-logs")
class ArticleUploaderStatusChangeLogController @Autowired
constructor(val articleUploaderStatusChangeLogService: ArticleUploaderStatusChangeLogService,
            val authTokenService: AuthTokenService,
            val restControllerUtills: RestControllerUtills) {

    @Value("\${log.default_page_size}")
    var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    var maxPageSize: Int = 50

    @GetMapping("")
    fun getLatestArticleUploaderStatusChangeLogs(@RequestParam("page-size") pageSizeRequest:Int?): ResponseEntity<List<ArticleUploaderStatusChangeLog>> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.listEntityToResponseEntity(articleUploaderStatusChangeLogService.getLatestArticleUploaderStatusChangeLogs(pageSize))
    }

    @GetMapping("/before/article-uploader-status-change-log-id/{log-id}")
    fun getArticleUploaderStatusChangeLogsBeforeGivenId(@RequestParam("page-size") pageSizeRequest:Int?,
                                        @PathVariable("log-id") lastStatusChangeLogId:Int)
            : ResponseEntity<List<ArticleUploaderStatusChangeLog>> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.listEntityToResponseEntity(articleUploaderStatusChangeLogService
                                                                    .getArticleUploaderStatusChangeLogsBeforeGivenId(lastStatusChangeLogId,pageSize))
    }

    @GetMapping("request_status_change_token_generation")
    fun generateStatusChangeToken():ResponseEntity<ArticleUploaderStatusChangeRequestFormat>{
        val newToken = authTokenService.getNewAuthToken()
        EmailUtils.emailAuthTokenToAdmin(newToken,this::class.java)
        return restControllerUtills.entityToResponseEntity(ArticleUploaderStatusChangeRequestFormat())
    }

    @PostMapping("status_change_request")
    fun changeUploaderStatus(@RequestBody articleUploaderStatusChangeRequest: ArticleUploaderStatusChangeRequest?)
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
