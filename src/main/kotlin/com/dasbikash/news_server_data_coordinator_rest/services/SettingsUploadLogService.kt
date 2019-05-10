package com.dasbikash.news_server_data_coordinator_rest.services

import com.dasbikash.news_server_data_coordinator_rest.exceptions.DataNotFoundException
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.SettingsUploadLog
import com.dasbikash.news_server_data_coordinator_rest.repositories.SettingsUploadLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SettingsUploadLogService @Autowired constructor(val settingsUploadLogRepository: SettingsUploadLogRepository){
    fun getLatestSettingsUploadLogs(pageSize: Int): List<SettingsUploadLog> {
        return settingsUploadLogRepository.getLatestSettingsUploadLogs(pageSize)
    }

    fun getSettingsUploadLogsBeforeGivenId(lastErrorLogId: Int, pageSize: Int): List<SettingsUploadLog> {
        val lastSettingsUploadLog = settingsUploadLogRepository.findById(lastErrorLogId)
        if (!lastSettingsUploadLog.isPresent){
            throw DataNotFoundException()
        }
        return settingsUploadLogRepository.getSettingsUpdateLogsBeforeGivenId(lastSettingsUploadLog.get().id!!,pageSize)
    }
}