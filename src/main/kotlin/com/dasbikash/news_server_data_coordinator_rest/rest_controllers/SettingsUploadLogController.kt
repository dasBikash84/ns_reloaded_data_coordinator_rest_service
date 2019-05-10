package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequest
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_data_coordinator_rest.model.SettingsUploadLogs
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.SettingsUploadLog
import com.dasbikash.news_server_data_coordinator_rest.services.SettingsUploadLogService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("settings-upload-logs")
class SettingsUploadLogController @Autowired
constructor(val settingsUploadLogService: SettingsUploadLogService,
            val restControllerUtills: RestControllerUtills) {

    @Value("\${log.default_page_size}")
    var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    var maxPageSize: Int = 50

    @GetMapping("")
    fun getLatestSettingsUploadLogs(@RequestParam("page-size") pageSizeRequest:Int?): ResponseEntity<SettingsUploadLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(SettingsUploadLogs(
                settingsUploadLogService.getLatestSettingsUploadLogs(pageSize)))
    }

    @GetMapping("/before/settings-upload-log-id/{log-id}")
    fun getSettingsUploadLogsBeforeGivenId(@RequestParam("page-size") pageSizeRequest:Int?,
                                        @PathVariable("log-id") lastErrorLogId:Int)
            : ResponseEntity<SettingsUploadLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(SettingsUploadLogs(
                        settingsUploadLogService.getSettingsUploadLogsBeforeGivenId(lastErrorLogId,pageSize)))
    }

    @DeleteMapping("request_log_delete_token_generation")
    fun generateLogDeletionToken(): ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtills.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("")
    fun deleteErrorLogs(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?): ResponseEntity<SettingsUploadLogs> {
        return restControllerUtills.entityToResponseEntity(SettingsUploadLogs(
                restControllerUtills.deleteLogEntries(settingsUploadLogService,logEntryDeleteRequest)))
    }
}
