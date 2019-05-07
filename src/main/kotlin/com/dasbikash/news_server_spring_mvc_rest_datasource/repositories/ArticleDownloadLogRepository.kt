package com.dasbikash.news_server_spring_mvc_rest_datasource.repositories

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.DatabaseTableNames
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.ArticleDownloadLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArticleDownloadLogRepository : JpaRepository<ArticleDownloadLog, Int>{
    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_DOWNLOAD_LOG_TABLE_NAME} order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getLatestArticleDownloadLogs(pageSize: Int): List<ArticleDownloadLog>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_DOWNLOAD_LOG_TABLE_NAME} WHERE id < :lastArticleDownloadLogId order by id DESC limit :pageSize",
            nativeQuery = true)
    fun getArticleDownloadLogsBeforeGivenId(lastArticleDownloadLogId: Int, pageSize: Int): List<ArticleDownloadLog>
}