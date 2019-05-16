package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequest
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_data_coordinator_rest.model.SettingsUpdateLogs
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.SettingsUpdateLog
import com.dasbikash.news_server_data_coordinator_rest.services.SettingsUpdateLogService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("settings-update-logs")
class SettingsUpdateLogController @Autowired
constructor(val settingsUpdateLogService: SettingsUpdateLogService,
            val restControllerUtills: RestControllerUtills) {

    @Value("\${log.default_page_size}")
    var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    var maxPageSize: Int = 50

    @GetMapping("")
    fun getLatestSettingsUpdateLogs(@RequestParam("page-size") pageSizeRequest:Int?): ResponseEntity<SettingsUpdateLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(SettingsUpdateLogs(
                            settingsUpdateLogService.getLatestSettingsUpdateLogs(pageSize)))
    }

    @GetMapping("/before/{log-id}")
    fun getSettingsUpdateLogsBeforeGivenId(@RequestParam("page-size") pageSizeRequest:Int?,
                                        @PathVariable("log-id") lastErrorLogId:Int)
            : ResponseEntity<SettingsUpdateLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(SettingsUpdateLogs(
                        settingsUpdateLogService.getSettingsUpdateLogsBeforeGivenId(lastErrorLogId,pageSize)))
    }

    @GetMapping("/after/{log-id}")
    fun getSettingsUpdateLogsAfterGivenId(@RequestParam("page-size") pageSizeRequest:Int?,
                                        @PathVariable("log-id") lastErrorLogId:Int)
            : ResponseEntity<SettingsUpdateLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(SettingsUpdateLogs(
                        settingsUpdateLogService.getLogsAfterGivenId(lastErrorLogId,pageSize)))
    }

    @DeleteMapping("request_log_delete_token_generation")
    fun generateLogDeletionToken(): ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtills.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("")
    fun deleteErrorLogs(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?): ResponseEntity<SettingsUpdateLogs> {
        return restControllerUtills.entityToResponseEntity(SettingsUpdateLogs(
                restControllerUtills.deleteLogEntries(settingsUpdateLogService,logEntryDeleteRequest)))
    }
}
