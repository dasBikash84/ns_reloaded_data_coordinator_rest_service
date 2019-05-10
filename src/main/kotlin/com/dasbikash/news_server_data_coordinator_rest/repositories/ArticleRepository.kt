package com.dasbikash.news_server_data_coordinator_rest.repositories

import com.dasbikash.news_server_data_coordinator_rest.model.database.Article
import com.dasbikash.news_server_data_coordinator_rest.model.database.Page
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, String>{
    fun findAllByPageAndArticleTextIsNotNullOrderByPublicationTimeDesc(page: Page):List<Article>
}