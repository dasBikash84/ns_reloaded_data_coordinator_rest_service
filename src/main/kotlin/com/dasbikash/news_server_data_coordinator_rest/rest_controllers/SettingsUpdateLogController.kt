package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequest
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_data_coordinator_rest.model.SettingsUpdateLogs
import com.dasbikash.news_server_data_coordinator_rest.services.SettingsUpdateLogService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("settings-update-logs",
        produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class SettingsUpdateLogController
constructor(open var settingsUpdateLogService: SettingsUpdateLogService,
            open var restControllerUtills: RestControllerUtills) {

    @Value("\${log.default_page_size}")
    open var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    open var maxPageSize: Int = 50

    @GetMapping("",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getLatestSettingsUpdateLogsEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                 @Autowired request: HttpServletRequest)
            : ResponseEntity<SettingsUpdateLogs> {
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

    @GetMapping("/before/{log-id}",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getSettingsUpdateLogsBeforeGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                        @PathVariable("log-id") lastErrorLogId:Int,
                                                        @Autowired request: HttpServletRequest)
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

    @GetMapping("/after/{log-id}",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getSettingsUpdateLogsAfterGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest:Int?,
                                                        @PathVariable("log-id") lastErrorLogId:Int,
                                                       @Autowired request: HttpServletRequest)
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

    @DeleteMapping("request_log_delete_token_generation",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun generateLogDeletionTokenEndPoint(@Autowired request: HttpServletRequest): ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtills.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("",
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun deleteErrorLogsEndPoint(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?,
                                     @Autowired request: HttpServletRequest)
            : ResponseEntity<SettingsUpdateLogs> {
        return restControllerUtills.entityToResponseEntity(SettingsUpdateLogs(
                restControllerUtills.deleteLogEntries(settingsUpdateLogService,logEntryDeleteRequest)))
    }
}
