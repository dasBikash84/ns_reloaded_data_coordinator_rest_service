package com.dasbikash.news_server_spring_mvc_rest_datasource.repositories

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.DatabaseTableNames
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.ErrorLog
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.SettingsUpdateLog
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.SettingsUploadLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SettingsUploadLogRepository : JpaRepository<SettingsUploadLog, Int>{

    @Query(value = "SELECT * FROM ${DatabaseTableNames.SETTINGS_UPLOAD_LOG_TABLE_NAME} order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getLatestSettingsUploadLogs(pageSize: Int): List<SettingsUploadLog>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.SETTINGS_UPLOAD_LOG_TABLE_NAME} WHERE id < :lastSettingsUploadLogId order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getSettingsUpdateLogsBeforeGivenId(lastSettingsUploadLogId: Int, pageSize: Int): List<SettingsUploadLog>
}