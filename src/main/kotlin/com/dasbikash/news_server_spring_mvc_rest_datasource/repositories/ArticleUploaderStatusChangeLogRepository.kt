package com.dasbikash.news_server_spring_mvc_rest_datasource.repositories

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.DatabaseTableNames
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.ArticleUploaderStatusChangeLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArticleUploaderStatusChangeLogRepository : JpaRepository<ArticleUploaderStatusChangeLog, Int>{

    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_UPLOADER_STATUS_CHANGE_LOG_TABLE_NAME} order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getLatestArticleUploaderStatusChangeLogs(pageSize: Int): List<ArticleUploaderStatusChangeLog>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_UPLOADER_STATUS_CHANGE_LOG_TABLE_NAME} WHERE id < :lastStatusChangeLogId order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getArticleDownloadLogsBeforeGivenId(lastStatusChangeLogId: Int, pageSize: Int): List<ArticleUploaderStatusChangeLog>
}