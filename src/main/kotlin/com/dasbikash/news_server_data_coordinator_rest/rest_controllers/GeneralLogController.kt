package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.GeneralLogs
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequest
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_data_coordinator_rest.model.database.DataCoordinatorRestEntity
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.GeneralLog
import com.dasbikash.news_server_data_coordinator_rest.services.AuthTokenService
import com.dasbikash.news_server_data_coordinator_rest.services.DeletableLogService
import com.dasbikash.news_server_data_coordinator_rest.services.GeneralLogService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("general-logs")
open class GeneralLogController @Autowired
constructor(open var generalLogService: GeneralLogService,
            open var restControllerUtills: RestControllerUtills) {

    @Value("\${log.default_page_size}")
    open var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    open var maxPageSize: Int = 50

    @GetMapping("")
    open fun getLatestGeneralLogsEndPoint(@RequestParam("page-size") pageSizeRequest: Int?,
                                          @Autowired request: HttpServletRequest): ResponseEntity<GeneralLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(GeneralLogs(generalLogService.getLatestGeneralLogs(pageSize)))
    }

    @GetMapping("/before/{log-id}")
    open fun getGeneralLogsBeforeGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest: Int?,
                                                @PathVariable("log-id") lastGeneralLogId: Int,
                                                 @Autowired request: HttpServletRequest)
            : ResponseEntity<GeneralLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(GeneralLogs(
                generalLogService.getGeneralLogsBeforeGivenId(lastGeneralLogId, pageSize)))
    }

    @GetMapping("/after/{log-id}")
    open fun getGeneralLogsAfterGivenIdEndPoint(@RequestParam("page-size") pageSizeRequest: Int?,
                                                @PathVariable("log-id") lastGeneralLogId: Int,
                                                @Autowired request: HttpServletRequest)
            : ResponseEntity<GeneralLogs> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(GeneralLogs(
                generalLogService.getLogsAfterGivenId(lastGeneralLogId, pageSize)))
    }

    @DeleteMapping("request_log_delete_token_generation")
    open fun generateLogDeletionTokenEndPoint(@Autowired request: HttpServletRequest): ResponseEntity<LogEntryDeleteRequestFormat> {
        return restControllerUtills.generateLogDeleteToken(this::class.java)
    }

    @DeleteMapping("")
    open fun deleteGeneralLogsEndPoint(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?,
                                       @Autowired request: HttpServletRequest)
            : ResponseEntity<GeneralLogs> {
        return restControllerUtills.entityToResponseEntity(GeneralLogs(
                restControllerUtills.deleteLogEntries(generalLogService,logEntryDeleteRequest)))
    }
}
