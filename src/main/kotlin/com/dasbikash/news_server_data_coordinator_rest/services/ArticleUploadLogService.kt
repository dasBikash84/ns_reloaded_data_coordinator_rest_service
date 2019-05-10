package com.dasbikash.news_server_data_coordinator_rest.services

import com.dasbikash.news_server_data_coordinator_rest.exceptions.DataNotFoundException
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.ArticleUploadLog
import com.dasbikash.news_server_data_coordinator_rest.repositories.ArticleUploadLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArticleUploadLogService @Autowired constructor(val articleUploadLogRepository: ArticleUploadLogRepository)
    :DeletableLogService<ArticleUploadLog>{

    fun getLatestArticleUploadLogs(pageSize: Int): List<ArticleUploadLog> {
        return articleUploadLogRepository.getLatestArticleUploadLogs(pageSize)
    }

    fun getArticleUploadLogsBeforeGivenId(lastArticleUploadLogId: Int, pageSize: Int): List<ArticleUploadLog> {
        val lastArticleUploadLog = articleUploadLogRepository.findById(lastArticleUploadLogId)
        if (!lastArticleUploadLog.isPresent){
            throw DataNotFoundException()
        }
        return articleUploadLogRepository.getSettingsUpdateLogsBeforeGivenId(lastArticleUploadLog.get().id!!,pageSize)
    }

    override fun getOldestLogs(pageSize: Int): List<ArticleUploadLog> {
        return articleUploadLogRepository.getOldestLogs(pageSize)
    }

    override fun getLogsAfterGivenId(lastLogId: Int, pageSize: Int): List<ArticleUploadLog> {
        return articleUploadLogRepository.getLogsAfterGivenId(lastLogId,pageSize)
    }

    override fun delete(logEntry: ArticleUploadLog) {
        articleUploadLogRepository.delete(logEntry)
    }
}