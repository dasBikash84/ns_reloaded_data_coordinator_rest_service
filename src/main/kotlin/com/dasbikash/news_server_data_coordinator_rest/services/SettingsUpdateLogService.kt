package com.dasbikash.news_server_data_coordinator_rest.services

import com.dasbikash.news_server_data_coordinator_rest.exceptions.DataNotFoundException
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.SettingsUpdateLog
import com.dasbikash.news_server_data_coordinator_rest.repositories.SettingsUpdateLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SettingsUpdateLogService @Autowired constructor(val settingsUpdateLogRepository: SettingsUpdateLogRepository)
    :DeletableLogService<SettingsUpdateLog>{
    fun getLatestSettingsUpdateLogs(pageSize: Int): List<SettingsUpdateLog> {
        val settingsUpdateLogs = settingsUpdateLogRepository.getLatestSettingsUpdateLogs(pageSize)
        if (settingsUpdateLogs.isEmpty()){
            throw DataNotFoundException()
        }
        return settingsUpdateLogs
    }

    fun getSettingsUpdateLogsBeforeGivenId(lastErrorLogId: Int, pageSize: Int): List<SettingsUpdateLog> {
        val lastSettingsUpdateLog = settingsUpdateLogRepository.findById(lastErrorLogId)
        if (!lastSettingsUpdateLog.isPresent){
            throw DataNotFoundException()
        }
        return settingsUpdateLogRepository.getSettingsUpdateLogsBeforeGivenId(lastSettingsUpdateLog.get().id!!,pageSize)
    }

    override fun getOldestLogs(pageSize: Int): List<SettingsUpdateLog> {
        return settingsUpdateLogRepository.getOldestLogs(pageSize)
    }

    override fun getLogsAfterGivenId(lastLogId: Int, pageSize: Int): List<SettingsUpdateLog> {
        return settingsUpdateLogRepository.getLogsAfterGivenId(lastLogId,pageSize)
    }

    override fun delete(logEntry: SettingsUpdateLog) {
        settingsUpdateLogRepository.delete(logEntry)
    }
}