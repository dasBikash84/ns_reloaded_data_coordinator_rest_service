package com.dasbikash.news_server_data_coordinator_rest.repositories

import com.dasbikash.news_server_data_coordinator_rest.model.database.Article
import com.dasbikash.news_server_data_coordinator_rest.model.database.DatabaseTableNames
import com.dasbikash.news_server_data_coordinator_rest.model.database.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArticleRepository : JpaRepository<Article, String>{
    fun findAllByPageAndArticleTextIsNotNullOrderByPublicationTimeDesc(page: Page):List<Article>

    @Query(value = "SELECT * FROM ${DatabaseTableNames.ARTICLE_TABLE_NAME} WHERE pageId=:pageId order by created DESC limit :pageSize",
            nativeQuery = true)
    fun getLatestArticlesByPageId(pageId: String, pageSize: Int): List<Article>
}