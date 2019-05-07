package com.dasbikash.news_server_spring_mvc_rest_datasource.rest_controllers

import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.DataNotFoundException
import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.IllegalRequestBodyException
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.ArticleUploaderStatusChangeRequest
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.ArticleUploaderStatusChangeRequestFormat
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.AuthToken
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.ArticleUploaderStatusChangeLog
import com.dasbikash.news_server_spring_mvc_rest_datasource.services.ArticleUploaderStatusChangeLogService
import com.dasbikash.news_server_spring_mvc_rest_datasource.utills.EmailUtils
import com.dasbikash.news_server_spring_mvc_rest_datasource.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("article-uploader-status-change-logs")
class ArticleUploaderStatusChangeLogController @Autowired
constructor(val articleUploaderStatusChangeLogService: ArticleUploaderStatusChangeLogService) {

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
        return RestControllerUtills.listEntityToResponseEntity(articleUploaderStatusChangeLogService.getLatestArticleUploaderStatusChangeLogs(pageSize))
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
        return RestControllerUtills.listEntityToResponseEntity(articleUploaderStatusChangeLogService
                                                                    .getArticleUploaderStatusChangeLogsBeforeGivenId(lastStatusChangeLogId,pageSize))
    }

    @GetMapping("request_status_change_token_generation")
    fun generateStatusChangeToken():ResponseEntity<ArticleUploaderStatusChangeRequestFormat>{
        val newToken = AuthToken()
        articleUploaderStatusChangeLogService.saveAuthToken(newToken)
        try {
            EmailUtils.sendEmail("New Article Uploader Status Change Token","Token:\t${newToken.token}\nExpires on: ${newToken.expiresOn}")
        }catch (ex:Exception){
            throw DataNotFoundException()
        }
        return RestControllerUtills.entityToResponseEntity(ArticleUploaderStatusChangeRequestFormat())
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
        println("Going to read token")
        val readToken = articleUploaderStatusChangeLogService
                                            .findAuthTokenByTokenValue(articleUploaderStatusChangeRequest.authToken)
        if (!readToken.isPresent || readToken.get().expiresOn < Date()){
            throw IllegalRequestBodyException()
        }
        println("Valid Token")

        val statusData = ArticleUploaderStatusChangeLog(
                            status = articleUploaderStatusChangeRequest.status,
                            articleDataUploaderTarget = articleUploaderStatusChangeRequest.articleUploadTarget)
        try {
            val authToken = readToken.get()
            authToken.expiresOn = Date()
            articleUploaderStatusChangeLogService.saveAuthToken(authToken)
            articleUploaderStatusChangeLogService.save(statusData)
            return RestControllerUtills.entityToResponseEntity(articleUploaderStatusChangeRequest)
        }catch (ex:Exception){
            throw InternalError()
        }
    }
}
