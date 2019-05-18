package com.dasbikash.news_server_data_coordinator_rest.utills

import com.dasbikash.news_server_data_coordinator_rest.exceptions.DataNotFoundException
import com.dasbikash.news_server_data_coordinator_rest.exceptions.IllegalRequestBodyException
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequest
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_data_coordinator_rest.model.database.DataCoordinatorRestEntity
import com.dasbikash.news_server_data_coordinator_rest.services.AuthTokenService
import com.dasbikash.news_server_data_coordinator_rest.services.DeletableLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
open class RestControllerUtills constructor(open var authTokenService: AuthTokenService) {

    fun <T : DataCoordinatorRestEntity> listEntityToResponseEntity(entityList: List<T>): ResponseEntity<List<T>> {
        if (entityList.isEmpty()) {
            throw DataNotFoundException()
        }
        return ResponseEntity.ok(entityList)
    }

    fun <T : DataCoordinatorRestEntity> entityToResponseEntity(entity: T?): ResponseEntity<T> {
        if (entity == null) {
            throw DataNotFoundException()
        }
        return ResponseEntity.ok(entity)
    }

    fun <T> generateLogDeleteToken(type: Class<T>): ResponseEntity<LogEntryDeleteRequestFormat> {
        val newToken = authTokenService.getNewAuthToken()
        EmailUtils.emailAuthTokenToAdmin(newToken, type)
        return entityToResponseEntity(LogEntryDeleteRequestFormat())
    }

    fun validateLogEntryDeleteRequest(logEntryDeleteRequest: LogEntryDeleteRequest?) {
        if (logEntryDeleteRequest == null ||
                logEntryDeleteRequest.authToken == null
        ) {
            throw IllegalRequestBodyException()
        }
    }

    fun <T : DataCoordinatorRestEntity> deleteLogEntries(deletableLogService: DeletableLogService<T>,
                                                         logEntryDeleteRequest: LogEntryDeleteRequest?)
            : List<T> {

        validateLogEntryDeleteRequest(logEntryDeleteRequest)
        authTokenService.invalidateAuthToken(logEntryDeleteRequest!!.authToken!!)

        val logEntriesForDeletion = mutableListOf<T>()

        if (logEntryDeleteRequest.targetLogId == null) {
            if (logEntryDeleteRequest.entryDeleteCount == null ||
                    logEntryDeleteRequest.entryDeleteCount!! < 0) {
                logEntriesForDeletion.addAll(deletableLogService.getOldestLogs(LogEntryDeleteRequest.DEFAULT_ENTRY_DELETE_COUNT))
            } else {
                if (logEntryDeleteRequest.entryDeleteCount!! > LogEntryDeleteRequest.MAX_ENTRY_DELETE_LIMIT) {
                    logEntriesForDeletion.addAll(deletableLogService
                            .getOldestLogs(LogEntryDeleteRequest.MAX_ENTRY_DELETE_LIMIT))
                } else {
                    logEntriesForDeletion.addAll(deletableLogService
                            .getOldestLogs(logEntryDeleteRequest.entryDeleteCount!!))
                }
            }
        } else {
            if (logEntryDeleteRequest.entryDeleteCount == null ||
                    logEntryDeleteRequest.entryDeleteCount!! < 0) {
                logEntriesForDeletion.addAll(deletableLogService.getLogsAfterGivenId(logEntryDeleteRequest.targetLogId, LogEntryDeleteRequest.DEFAULT_ENTRY_DELETE_COUNT))
            } else {
                if (logEntryDeleteRequest.entryDeleteCount!! > LogEntryDeleteRequest.MAX_ENTRY_DELETE_LIMIT) {
                    logEntriesForDeletion.addAll(deletableLogService
                            .getLogsAfterGivenId(logEntryDeleteRequest.targetLogId, LogEntryDeleteRequest.MAX_ENTRY_DELETE_LIMIT))
                } else {
                    logEntriesForDeletion.addAll(deletableLogService
                            .getLogsAfterGivenId(logEntryDeleteRequest.targetLogId, logEntryDeleteRequest.entryDeleteCount!!))
                }
            }
        }
        logEntriesForDeletion.asSequence().forEach {
            deletableLogService.delete(it)
        }
        return logEntriesForDeletion
    }
}