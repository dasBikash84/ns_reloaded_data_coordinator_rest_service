package com.dasbikash.news_server_spring_mvc_rest_datasource.repositories

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.DatabaseTableNames
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.ErrorLog
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.SettingsUpdateLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SettingsUpdateLogRepository : JpaRepository<SettingsUpdateLog, Int>{

    @Query(value = "SELECT * FROM ${DatabaseTableNames.SETTINGS_UPDATE_LOG_TABLE_NAME} order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getLatestSettingsUpdateLogs(pageSize: Int): List<SettingsUpdateLog>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.SETTINGS_UPDATE_LOG_TABLE_NAME} WHERE id < :lastSettingsUpdateLogId order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getSettingsUpdateLogsBeforeGivenId(lastSettingsUpdateLogId: Int, pageSize: Int): List<SettingsUpdateLog>
}