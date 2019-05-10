package com.dasbikash.news_server_data_coordinator_rest.services

import com.dasbikash.news_server_data_coordinator_rest.exceptions.DataNotFoundException
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.ErrorLog
import com.dasbikash.news_server_data_coordinator_rest.repositories.ErrorLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ErrorLogService @Autowired constructor(val errorLogRepository: ErrorLogRepository){
    fun getLatestErrorLogs(pageSize: Int): List<ErrorLog> {
        return errorLogRepository.getLatestErrorLogs(pageSize)
    }

    fun getErrorLogsBeforeGivenId(lastErrorLogId: Int, pageSize: Int): List<ErrorLog> {
        val lastErrorLog = errorLogRepository.findById(lastErrorLogId)
        if (!lastErrorLog.isPresent){
            throw DataNotFoundException()
        }
        return errorLogRepository.getErrorLogsBeforeGivenId(lastErrorLog.get().id!!,pageSize)
    }
}