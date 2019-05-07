package com.dasbikash.news_server_spring_mvc_rest_datasource.services

import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.DataNotFoundException
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.GeneralLog
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.GeneralLogRepository
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
}