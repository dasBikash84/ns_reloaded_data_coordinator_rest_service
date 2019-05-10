package com.dasbikash.news_server_data_coordinator_rest.services

import com.dasbikash.news_server_data_coordinator_rest.exceptions.DataNotFoundException
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.GeneralLog
import com.dasbikash.news_server_data_coordinator_rest.repositories.GeneralLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GeneralLogService @Autowired constructor(val generalLogRepository: GeneralLogRepository){
    fun getLatestGeneralLogs(pageSize: Int): List<GeneralLog> {
        return generalLogRepository.getLatestGeneralLogs(pageSize)
    }

    fun getGeneralLogsBeforeGivenId(lastGeneralLogId: Int, pageSize: Int): List<GeneralLog> {
        val lastGeneralLog = generalLogRepository.findById(lastGeneralLogId)
        if (!lastGeneralLog.isPresent){
            throw DataNotFoundException()
        }
        return generalLogRepository.getSettingsUpdateLogsBeforeGivenId(lastGeneralLog.get().id!!,pageSize)
    }
    fun getOldestGeneralLogs(pageSize: Int): List<GeneralLog> {
        return generalLogRepository.getOldestGeneralLogs(pageSize)
    }

    fun getGeneralLogsAfterGivenId(lastGeneralLogId: Int, pageSize: Int): List<GeneralLog> {
        val lastGeneralLog = generalLogRepository.findById(lastGeneralLogId)
        if (!lastGeneralLog.isPresent){
            throw DataNotFoundException()
        }
        return generalLogRepository.getGeneralLogsAfterGivenId(lastGeneralLog.get().id!!,pageSize)
    }
    fun delete(generalLog: GeneralLog){
        generalLogRepository.delete(generalLog)
    }
}