package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.ArticleUploadLogs
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequest
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.ArticleUploadLog
import com.dasbikash.news_server_data_coordinator_rest.services.ArticleUploadLogService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("article-upload-logs")
open class ArticleUploadLogController
constructor(open var  articleUploadLogService: ArticleUploadLogService,
            open var  restControllerUtills: RestControllerUtills) {

    @Value("\${log.default_page_size}")
    open var  defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    open var  maxPageSize: Int = 50

    @GetMapping("")
    open fun getLatestArticleUploadLogsEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                @Autowired request: HttpServletRequest)
            : ResponseEntity<ArticleUploadLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(ArticleUploadLogs(
                        articleUploadLogService.getLatestArticleUploadLogs(pageSize)))
    }

    @GetMapping("/before/{log-id}")
    open fun getArticleUploadLogsBeforeGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                        @PathVariable("log-id") lastArticleUploadLogId:Int,
                                                       @Autowired request: HttpServletRequest)
            : ResponseEntity<ArticleUploadLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(ArticleUploadLogs(
                        articleUploadLogService.getArticleUploadLogsBeforeGivenId(lastArticleUploadLogId,pageSize)))
    }

    @GetMapping("/after/{log-id}")
    open fun getArticleUploadLogsAfterGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                    @PathVariable("log-id") lastArticleUploadLogId:Int,
                                                      @Autowired request: HttpServletRequest)
            : ResponseEntity<ArticleUploadLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(ArticleUploadLogs(
                        articleUploadLogService.getLogsAfterGivenId(lastArticleUploadLogId,pageSize)))
    }

    @DeleteMapping("request_log_delete_token_generation")
    open fun generateLogDeletionTokenEndPoint(@Autowired request: HttpServletRequest): ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtills.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("")
    open fun deleteErrorLogsEndPoint(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?,
                                     @Autowired request: HttpServletRequest)
            : ResponseEntity<ArticleUploadLogs> {
        return restControllerUtills.entityToResponseEntity(ArticleUploadLogs(
                restControllerUtills.deleteLogEntries(articleUploadLogService,logEntryDeleteRequest)))
    }
}
