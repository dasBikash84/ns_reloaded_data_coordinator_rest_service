package com.dasbikash.news_server_data_coordinator_rest.services

import com.dasbikash.news_server_data_coordinator_rest.model.database.DataCoordinatorRestEntity

interface DeletableLogService<T:DataCoordinatorRestEntity> {
    fun getOldestLogs(pageSize: Int): List<T>
    fun getLogsAfterGivenId(lastLogId: Int, pageSize: Int): List<T>
    fun delete(logEntry: T)
}