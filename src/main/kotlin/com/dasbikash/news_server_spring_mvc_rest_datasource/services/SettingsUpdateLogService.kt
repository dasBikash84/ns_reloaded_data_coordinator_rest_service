package com.dasbikash.news_server_spring_mvc_rest_datasource.services

import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.DataNotFoundException
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.SettingsUpdateLog
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.SettingsUpdateLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SettingsUpdateLogService @Autowired constructor(val settingsUpdateLogRepository: SettingsUpdateLogRepository){
    fun getLatestSettingsUpdateLogs(pageSize: Int): List<SettingsUpdateLog> {
        return settingsUpdateLogRepository.getLatestSettingsUpdateLogs(pageSize)
    }

    fun getSettingsUpdateLogsBeforeGivenId(lastErrorLogId: Int, pageSize: Int): List<SettingsUpdateLog> {
        val lastSettingsUpdateLog = settingsUpdateLogRepository.findById(lastErrorLogId)
        if (!lastSettingsUpdateLog.isPresent){
            throw DataNotFoundException()
        }
        return settingsUpdateLogRepository.getSettingsUpdateLogsBeforeGivenId(lastSettingsUpdateLog.get().id!!,pageSize)
    }
}