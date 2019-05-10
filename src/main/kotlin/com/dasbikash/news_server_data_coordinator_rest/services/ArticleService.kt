package com.dasbikash.news_server_data_coordinator_rest.services

import com.dasbikash.news_server_data_coordinator_rest.exceptions.DataNotFoundException
import com.dasbikash.news_server_data_coordinator_rest.model.database.Article
import com.dasbikash.news_server_data_coordinator_rest.model.database.Page
import com.dasbikash.news_server_data_coordinator_rest.repositories.ArticleRepository
import com.dasbikash.news_server_data_coordinator_rest.repositories.PageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ArticleService
@Autowired constructor(val pageRepository: PageRepository, val articleRepository: ArticleRepository) {

    private fun getArticlesByPage(page: Page, pageSize: Int, lastArticleId: String? = null): List<Article> {

        val article = Article()
        article.page = page
        val exampleArticle = org.springframework.data.domain.Example.of(article)

        val retrivedData = articleRepository.findAll(exampleArticle)

        if (retrivedData.isEmpty()) {
            throw DataNotFoundException()
        }

        val firstArticle = retrivedData.asSequence().first { it.articleText != null }
        if (firstArticle == null) {
            throw DataNotFoundException()
        }

        val sort: Sort
        sort = Sort.by(Sort.Direction.DESC, "publicationTime")

        var articleSequence = articleRepository.findAll(exampleArticle, sort).asSequence().filter { it.articleText != null }


        if (lastArticleId != null) {
            articleSequence = articleSequence
                    .dropWhile { it.id != lastArticleId } //drop untill last article
                    .drop(1) //drop last article
        }

        val articles = mutableListOf<Article>()
        articleSequence
                .take(pageSize)
                .toCollection(articles)

        if (articles.size == 0) {
            throw DataNotFoundException()
        }
        articles.asSequence()
                .forEach {
                    if (it.previewImageLink == null && it.imageLinkList.size > 0) {
                        it.previewImageLink = it.imageLinkList.first().link
                    }
                }

        return articles
    }

    fun getArticlesByPageId(pageId: String, pageSize: Int, lastArticleId: String? = null): List<Article> {

        val pageOptional = pageRepository.findById(pageId)
        if (!pageOptional.isPresent || !pageOptional.get().hasData!!) {
            throw DataNotFoundException()
        }
        val page = pageOptional.get()

        return getArticlesByPage(page, pageSize, lastArticleId)
    }

    fun getArticlesForPageAfterLast(pageId: String, pageSize: Int, lastArticleId: String): List<Article> {
        return getArticlesByPageId(pageId, pageSize, lastArticleId)
    }

    fun getLatestArticleForTopLevelPage(topLevelPageId: String): Article? {
        val pageOptional = pageRepository.findById(topLevelPageId)
        if (!pageOptional.isPresent || !pageOptional.get().topLevelPage!!) {
            throw DataNotFoundException()
        }
        val topLevelPage = pageOptional.get()
        val page: Page
        if (topLevelPage.hasData!!) {
            page = topLevelPage
        } else {
            page = pageRepository.findPagesByParentPageIdAndActiveOrderByIdAsc(topLevelPage.id).first()
        }
        val articleList = articleRepository.findAllByPageAndArticleTextIsNotNullOrderByPublicationTimeDesc(page)
        if (articleList.size == 0) {
            return null
        }
        val article = articleList.first()
        if (article.previewImageLink == null && article.imageLinkList.size > 0) {
            article.previewImageLink = article.imageLinkList.first().link
        }
        return article
    }
}