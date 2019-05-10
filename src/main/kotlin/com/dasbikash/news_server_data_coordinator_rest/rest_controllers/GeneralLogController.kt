package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.exceptions.IllegalRequestBodyException
import com.dasbikash.news_server_data_coordinator_rest.model.ArticleUploaderStatusChangeRequest
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequest
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.GeneralLog
import com.dasbikash.news_server_data_coordinator_rest.services.AuthTokenService
import com.dasbikash.news_server_data_coordinator_rest.services.GeneralLogService
import com.dasbikash.news_server_data_coordinator_rest.utills.EmailUtils
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("general-logs")
class GeneralLogController @Autowired
constructor(val generalLogService: GeneralLogService,
            val authTokenService: AuthTokenService) {

    @Value("\${log.default_page_size}")
    var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    var maxPageSize: Int = 50

    @GetMapping("")
    fun getLatestGeneralLogs(@RequestParam("page-size") pageSizeRequest: Int?): ResponseEntity<List<GeneralLog>> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return RestControllerUtills.listEntityToResponseEntity(generalLogService.getLatestGeneralLogs(pageSize))
    }

    @GetMapping("/before/general-log-id/{log-id}")
    fun getGeneralLogsBeforeGivenId(@RequestParam("page-size") pageSizeRequest: Int?,
                                    @PathVariable("log-id") lastGeneralLogId: Int)
            : ResponseEntity<List<GeneralLog>> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when {
                it >= maxPageSize -> pageSize = maxPageSize
                it > 0 -> pageSize = it
            }
        }
        return RestControllerUtills.listEntityToResponseEntity(generalLogService.getGeneralLogsBeforeGivenId(lastGeneralLogId, pageSize))
    }

    @DeleteMapping("request_log_delete_token_generation")
    fun generateLogDeletionToken(): ResponseEntity<LogEntryDeleteRequestFormat> {
        return RestControllerUtills.generateLogDeleteToken(authTokenService, this::class.java)
    }

    @DeleteMapping("")
    fun deleteGeneralLogs(@RequestBody logEntryDeleteRequest: LogEntryDeleteRequest?): ResponseEntity<List<GeneralLog>> {

        RestControllerUtills.validateLogEntryDeleteRequest(logEntryDeleteRequest)
        authTokenService.invalidateAuthToken(logEntryDeleteRequest!!.authToken!!)

        val logEntriesForDeletion = mutableListOf<GeneralLog>()

        if (logEntryDeleteRequest.targetLogId==null){
            if (logEntryDeleteRequest.entryDeleteCount == null ||
                    logEntryDeleteRequest.entryDeleteCount!! < 0){
                logEntriesForDeletion.addAll(generalLogService.getOldestGeneralLogs(LogEntryDeleteRequest.DEFAULT_ENTRY_DELETE_COUNT))
            }else{
                if (logEntryDeleteRequest.entryDeleteCount!! > LogEntryDeleteRequest.MAX_ENTRY_DELETE_LIMIT){
                    logEntriesForDeletion.addAll(generalLogService
                                                    .getOldestGeneralLogs(LogEntryDeleteRequest.MAX_ENTRY_DELETE_LIMIT))
                }else{
                    logEntriesForDeletion.addAll(generalLogService
                                                    .getOldestGeneralLogs(logEntryDeleteRequest.entryDeleteCount!!))
                }
            }
        }else{
            if (logEntryDeleteRequest.entryDeleteCount == null ||
                    logEntryDeleteRequest.entryDeleteCount!! < 0){
                logEntriesForDeletion.addAll(generalLogService.getGeneralLogsAfterGivenId(logEntryDeleteRequest.targetLogId,LogEntryDeleteRequest.DEFAULT_ENTRY_DELETE_COUNT))
            }else{
                if (logEntryDeleteRequest.entryDeleteCount!! > LogEntryDeleteRequest.MAX_ENTRY_DELETE_LIMIT){
                    logEntriesForDeletion.addAll(generalLogService
                            .getGeneralLogsAfterGivenId(logEntryDeleteRequest.targetLogId,LogEntryDeleteRequest.MAX_ENTRY_DELETE_LIMIT))
                }else{
                    logEntriesForDeletion.addAll(generalLogService
                            .getGeneralLogsAfterGivenId(logEntryDeleteRequest.targetLogId, logEntryDeleteRequest.entryDeleteCount!!))
                }
            }
        }
        logEntriesForDeletion.asSequence().forEach {
            generalLogService.delete(it)
        }
        return RestControllerUtills.listEntityToResponseEntity(logEntriesForDeletion)
    }
}
