package com.dasbikash.news_server_data_coordinator_rest.repositories

import com.dasbikash.news_server_data_coordinator_rest.model.database.DatabaseTableNames
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.ArticleUploadLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArticleUploadLogRepository : JpaRepository<ArticleUploadLog, Int>{

    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_UPLOAD_LOG_TABLE_NAME} order by id DESC limit :pageSize",
            nativeQuery = true)fun getLatestArticleUploadLogs(pageSize: Int): List<ArticleUploadLog>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_UPLOAD_LOG_TABLE_NAME} WHERE id < :lastArticleUploadLogId order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getSettingsUpdateLogsBeforeGivenId(lastArticleUploadLogId: Int, pageSize: Int): List<ArticleUploadLog>
}