package com.dasbikash.news_server_spring_mvc_rest_datasource.rest_controllers

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.ArticleUploaderStatusChangeLog
import com.dasbikash.news_server_spring_mvc_rest_datasource.services.ArticleUploaderStatusChangeLogService
import com.dasbikash.news_server_spring_mvc_rest_datasource.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
}
