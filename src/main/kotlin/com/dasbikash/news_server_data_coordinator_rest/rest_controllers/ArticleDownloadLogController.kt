package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequest
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.ArticleDownloadLog
import com.dasbikash.news_server_data_coordinator_rest.services.ArticleDownloadLogService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("article-download-logs")
class ArticleDownloadLogController @Autowired
constructor(val articleDownloadLogService: ArticleDownloadLogService,
            val restControllerUtills: RestControllerUtills) {

    @Value("\${log.default_page_size}")
    var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    var maxPageSize: Int = 50

    @GetMapping("")
    fun getLatestArticleDownloadLogs(@RequestParam("page-size") pageSizeRequest:Int?): ResponseEntity<List<ArticleDownloadLog>> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.listEntityToResponseEntity(articleDownloadLogService.getLatestArticleDownloadLogs(pageSize))
    }

    @GetMapping("/before/article-download-log-id/{log-id}")
    fun getArticleDownloadLogsBeforeGivenId(@RequestParam("page-size") pageSizeRequest:Int?,
                                        @PathVariable("log-id") lastArticleDownloadLogId:Int)
            : ResponseEntity<List<ArticleDownloadLog>> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.listEntityToResponseEntity(articleDownloadLogService.getArticleDownloadLogsBeforeGivenId(lastArticleDownloadLogId,pageSize))
    }

    @DeleteMapping("request_log_delete_token_generation")
    fun generateLogDeletionToken(): ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtills.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("")
    fun deleteErrorLogs(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?): ResponseEntity<List<ArticleDownloadLog>> {
        return restControllerUtills.deleteLogEntries(articleDownloadLogService,logEntryDeleteRequest)
    }
}
