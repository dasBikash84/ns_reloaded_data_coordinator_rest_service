package com.dasbikash.news_server_spring_mvc_rest_datasource.rest_controllers

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.ArticleDownloadLog
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.ArticleUploadLog
import com.dasbikash.news_server_spring_mvc_rest_datasource.services.ArticleDownloadLogService
import com.dasbikash.news_server_spring_mvc_rest_datasource.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("article-download-logs")
class ArticleDownloadLogController @Autowired
constructor(val articleDownloadLogService: ArticleDownloadLogService) {

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
        return RestControllerUtills.listEntityToResponseEntity(articleDownloadLogService.getLatestArticleDownloadLogs(pageSize))
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
        return RestControllerUtills.listEntityToResponseEntity(articleDownloadLogService.getArticleDownloadLogsBeforeGivenId(lastArticleDownloadLogId,pageSize))
    }
}
