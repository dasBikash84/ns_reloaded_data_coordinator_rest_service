package com.dasbikash.news_server_data_coordinator_rest.utills

import com.dasbikash.news_server_data_coordinator_rest.exceptions.DataNotFoundException
import com.dasbikash.news_server_data_coordinator_rest.exceptions.IllegalRequestBodyException
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequest
import com.dasbikash.news_server_data_coordinator_rest.model.LogEntryDeleteRequestFormat
import com.dasbikash.news_server_data_coordinator_rest.model.database.DataCoordinatorRestEntity
import com.dasbikash.news_server_data_coordinator_rest.services.AuthTokenService
import org.springframework.http.ResponseEntity

object RestControllerUtills {
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

    fun <T> generateLogDeleteToken(authTokenService:AuthTokenService,type:Class<T>):ResponseEntity<LogEntryDeleteRequestFormat>{
        val newToken = authTokenService.getNewAuthToken()
        EmailUtils.emailAuthTokenToAdmin(newToken,type)
        return entityToResponseEntity(LogEntryDeleteRequestFormat())
    }

    fun validateLogEntryDeleteRequest(logEntryDeleteRequest: LogEntryDeleteRequest?){
        if (logEntryDeleteRequest == null ||
                logEntryDeleteRequest.authToken == null
        ) {
            throw IllegalRequestBodyException()
        }
    }

    class LogDeleteParam(
        val pageSize:Int,
        val firstId: Int
    )
}