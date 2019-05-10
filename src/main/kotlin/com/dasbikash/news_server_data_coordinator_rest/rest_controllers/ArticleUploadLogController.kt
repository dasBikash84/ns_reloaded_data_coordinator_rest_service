package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.ArticleUploadLog
import com.dasbikash.news_server_data_coordinator_rest.services.ArticleUploadLogService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("article-upload-logs")
class ArticleUploadLogController @Autowired
constructor(val articleUploadLogService: ArticleUploadLogService) {

    @Value("\${log.default_page_size}")
    var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    var maxPageSize: Int = 50

    @GetMapping("")
    fun getLatestArticleUploadLogs(@RequestParam("page-size") pageSizeRequest:Int?): ResponseEntity<List<ArticleUploadLog>> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return RestControllerUtills.listEntityToResponseEntity(articleUploadLogService.getLatestArticleUploadLogs(pageSize))
    }

    @GetMapping("/before/article-upload-log-id/{log-id}")
    fun getArticleUploadLogsBeforeGivenId(@RequestParam("page-size") pageSizeRequest:Int?,
                                        @PathVariable("log-id") lastArticleUploadLogId:Int)
            : ResponseEntity<List<ArticleUploadLog>> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return RestControllerUtills.listEntityToResponseEntity(articleUploadLogService.getArticleUploadLogsBeforeGivenId(lastArticleUploadLogId,pageSize))
    }
}
