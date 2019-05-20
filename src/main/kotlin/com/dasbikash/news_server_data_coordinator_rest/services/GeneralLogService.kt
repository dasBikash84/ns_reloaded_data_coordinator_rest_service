package com.dasbikash.news_server_data_coordinator_rest.services

import com.dasbikash.news_server_data_coordinator_rest.exceptions.DataNotFoundException
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.GeneralLog
import com.dasbikash.news_server_data_coordinator_rest.repositories.GeneralLogRepository
import org.springframework.stereotype.Service

@Service
open class GeneralLogService
constructor(open var generalLogRepository: GeneralLogRepository):DeletableLogService<GeneralLog>{
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
    override fun getOldestLogs(pageSize: Int): List<GeneralLog> {
        return generalLogRepository.getOldestLogs(pageSize)
    }

    override fun getLogsAfterGivenId(lastGeneralLogId: Int, pageSize: Int): List<GeneralLog> {
        val lastGeneralLog = generalLogRepository.findById(lastGeneralLogId)
        if (!lastGeneralLog.isPresent){
            throw DataNotFoundException()
        }
        return generalLogRepository.getLogsAfterGivenId(lastGeneralLog.get().id!!,pageSize)
    }
    override fun delete(generalLog: GeneralLog){
        generalLogRepository.delete(generalLog)
    }
}