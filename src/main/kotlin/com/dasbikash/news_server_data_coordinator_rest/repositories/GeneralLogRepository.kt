package com.dasbikash.news_server_data_coordinator_rest.repositories

import com.dasbikash.news_server_data_coordinator_rest.model.database.DatabaseTableNames
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.GeneralLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GeneralLogRepository : JpaRepository<GeneralLog, Int>{
    @Query(value = "SELECT * FROM ${DatabaseTableNames.GENERAL_LOG_TABLE_NAME} order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getLatestGeneralLogs(pageSize: Int): List<GeneralLog>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.GENERAL_LOG_TABLE_NAME} WHERE id < :lastGeneralLogId order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getSettingsUpdateLogsBeforeGivenId(lastGeneralLogId: Int, pageSize: Int): List<GeneralLog>
}