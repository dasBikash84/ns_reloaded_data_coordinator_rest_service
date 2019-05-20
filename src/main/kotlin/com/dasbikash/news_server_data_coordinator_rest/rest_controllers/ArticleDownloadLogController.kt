package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.ArticleDownloadLogs
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequest
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_data_coordinator_rest.services.ArticleDownloadLogService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("article-download-logs",
        produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class ArticleDownloadLogController
constructor(open var articleDownloadLogService: ArticleDownloadLogService,
            open var restControllerUtills: RestControllerUtills) {

    @Value("\${log.default_page_size}")
    open var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    open var maxPageSize: Int = 50

    @GetMapping("",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getLatestArticleDownloadLogsEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                  @Autowired request: HttpServletRequest):
            ResponseEntity<ArticleDownloadLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(
                        ArticleDownloadLogs(articleDownloadLogService.getLatestArticleDownloadLogs(pageSize)))
    }

    @GetMapping("/before/{log-id}",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getArticleDownloadLogsBeforeGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                        @PathVariable("log-id") lastArticleDownloadLogId:Int,
                                                         @Autowired request: HttpServletRequest)
            : ResponseEntity<ArticleDownloadLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(
                        ArticleDownloadLogs(articleDownloadLogService.getArticleDownloadLogsBeforeGivenId(lastArticleDownloadLogId,pageSize)))
    }

    @GetMapping("/after/{log-id}",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getArticleDownloadLogsAfterGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                        @PathVariable("log-id") lastArticleDownloadLogId:Int,
                                                        @Autowired request: HttpServletRequest)
            : ResponseEntity<ArticleDownloadLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(
                        ArticleDownloadLogs(articleDownloadLogService.getLogsAfterGivenId(lastArticleDownloadLogId,pageSize)))
    }

    @DeleteMapping("request_log_delete_token_generation",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun generateLogDeletionTokenEndPoint(@Autowired request: HttpServletRequest): ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtills.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun deleteErrorLogsEndPoint(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?,
                                     @Autowired request: HttpServletRequest)
            : ResponseEntity<ArticleDownloadLogs> {
        return restControllerUtills.entityToResponseEntity(ArticleDownloadLogs(
                restControllerUtills.deleteLogEntries(articleDownloadLogService,logEntryDeleteRequest)))
    }
}
