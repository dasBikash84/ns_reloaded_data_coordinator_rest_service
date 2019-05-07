package com.dasbikash.news_server_spring_mvc_rest_datasource.rest_controllers

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.SettingsUploadLog
import com.dasbikash.news_server_spring_mvc_rest_datasource.services.SettingsUploadLogService
import com.dasbikash.news_server_spring_mvc_rest_datasource.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("settings-upload-logs")
class SettingsUploadLogController @Autowired
constructor(val settingsUploadLogService: SettingsUploadLogService) {

    @Value("\${log.default_page_size}")
    var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    var maxPageSize: Int = 50

    @GetMapping("")
    fun getLatestSettingsUploadLogs(@RequestParam("page-size") pageSizeRequest:Int?): ResponseEntity<List<SettingsUploadLog>> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return RestControllerUtills.listEntityToResponseEntity(settingsUploadLogService.getLatestSettingsUploadLogs(pageSize))
    }

    @GetMapping("/before/settings-upload-log-id/{log-id}")
    fun getSettingsUploadLogsBeforeGivenId(@RequestParam("page-size") pageSizeRequest:Int?,
                                        @PathVariable("log-id") lastErrorLogId:Int)
            : ResponseEntity<List<SettingsUploadLog>> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return RestControllerUtills.listEntityToResponseEntity(settingsUploadLogService.getSettingsUploadLogsBeforeGivenId(lastErrorLogId,pageSize))
    }
}
