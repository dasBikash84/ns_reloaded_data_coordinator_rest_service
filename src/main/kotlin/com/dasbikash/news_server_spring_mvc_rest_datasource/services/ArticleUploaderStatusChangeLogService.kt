package com.dasbikash.news_server_spring_mvc_rest_datasource.services

import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.DataNotFoundException
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.AuthToken
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.ArticleUploaderStatusChangeLog
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.ArticleUploaderStatusChangeLogRepository
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.AuthTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ArticleUploaderStatusChangeLogService @Autowired
constructor(val articleUploaderStatusChangeLogRepository: ArticleUploaderStatusChangeLogRepository,
            val authTokenRepository: AuthTokenRepository){
    fun getLatestArticleUploaderStatusChangeLogs(pageSize: Int): List<ArticleUploaderStatusChangeLog> {
        return articleUploaderStatusChangeLogRepository.getLatestArticleUploaderStatusChangeLogs(pageSize)
    }

    fun getArticleUploaderStatusChangeLogsBeforeGivenId(lastStatusChangeLogId: Int, pageSize: Int): List<ArticleUploaderStatusChangeLog> {
        val lastStatusChangeLog = articleUploaderStatusChangeLogRepository.findById(lastStatusChangeLogId)
        if (!lastStatusChangeLog.isPresent){
            throw DataNotFoundException()
        }
        return articleUploaderStatusChangeLogRepository.getArticleDownloadLogsBeforeGivenId(lastStatusChangeLog.get().id!!,pageSize)
    }

    fun saveAuthToken(authToken: AuthToken):AuthToken{
        return authTokenRepository.save(authToken)
    }

    fun save(articleUploaderStatusChangeLog: ArticleUploaderStatusChangeLog):ArticleUploaderStatusChangeLog{
        return articleUploaderStatusChangeLogRepository.save(articleUploaderStatusChangeLog)
    }

    fun findAuthTokenByTokenValue(token: String):Optional<AuthToken>{
        return authTokenRepository.findById(token)
    }
}