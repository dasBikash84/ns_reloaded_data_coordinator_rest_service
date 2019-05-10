package com.dasbikash.news_server_data_coordinator_rest.model

import com.dasbikash.news_server_data_coordinator_rest.model.database.DataCoordinatorRestEntity

class LogEntryDeleteRequest(
        val authToken: String? = null,
        val targetLogId: Int? = null,
        val startLogId: Int? = null,
        val entryDeleteCount: Int? = null
) : DataCoordinatorRestEntity {
    companion object {
        const val MAX_ENTRY_DELETE_LIMIT = 50
    }
}

class LogEntryDeleteRequestFormat (
        val authToken:String = "Emailed token",
        val targetLogId: String = "Log id for Single entry delete request",
        val startLogId: String = "Starting Log id for Multiple entry delete request",
        val entryDeleteCount: String = "Log enyty delete count. Max:${LogEntryDeleteRequest.MAX_ENTRY_DELETE_LIMIT}"
): DataCoordinatorRestEntity