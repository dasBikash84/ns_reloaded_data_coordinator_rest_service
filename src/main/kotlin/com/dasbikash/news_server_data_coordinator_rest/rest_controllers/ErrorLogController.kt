package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.ErrorLogs
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequest
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.ErrorLog
import com.dasbikash.news_server_data_coordinator_rest.services.AuthTokenService
import com.dasbikash.news_server_data_coordinator_rest.services.ErrorLogService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("error-logs")
class ErrorLogController @Autowired
constructor(val errorLogService: ErrorLogService,
            val restControllerUtills: RestControllerUtills) {

    @Value("\${log.default_page_size}")
    var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    var maxPageSize: Int = 50

    @GetMapping("")
    fun getLatestErrorLogs(@RequestParam("page-size") pageSizeRequest:Int?): ResponseEntity<ErrorLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(ErrorLogs(errorLogService.getLatestErrorLogs(pageSize)))
    }

    @GetMapping("/before/{log-id}")
    fun getErrorLogsBeforeGivenId(@RequestParam("page-size") pageSizeRequest:Int?,
                                        @PathVariable("log-id") lastErrorLogId:Int)
            : ResponseEntity<ErrorLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(ErrorLogs(
                        errorLogService.getErrorLogsBeforeGivenId(lastErrorLogId,pageSize)))
    }

    @GetMapping("/after/{log-id}")
    fun getErrorLogsAfterGivenId(@RequestParam("page-size") pageSizeRequest:Int?,
                                        @PathVariable("log-id") lastErrorLogId:Int)
            : ResponseEntity<ErrorLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(ErrorLogs(
                        errorLogService.getLogsAfterGivenId(lastErrorLogId,pageSize)))
    }

    @DeleteMapping("request_log_delete_token_generation")
    fun generateLogDeletionToken(): ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtills.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("")
    fun deleteErrorLogs(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?): ResponseEntity<ErrorLogs> {
        return restControllerUtills.entityToResponseEntity(ErrorLogs(
                restControllerUtills.deleteLogEntries(errorLogService,logEntryDeleteRequest)))
    }
}
