package com.dasbikash.news_server_spring_mvc_rest_datasource.services

import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.DataNotFoundException
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Article
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.ArticleRepository
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.PageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ArticleService
@Autowired constructor(val pageRepository: PageRepository,val articleRepository: ArticleRepository) {

    fun getLatestArticlesForPage(pageId: String, pageSize:Int, lastArticleId:String?=null): List<Article> {

        val pageOptional = pageRepository.findById(pageId)
        if (!pageOptional.isPresent){
            throw DataNotFoundException()
        }
        val page = pageOptional.get()

        val article = Article()
        article.page = page
        val exampleArticle = org.springframework.data.domain.Example.of(article)

        val retrivedData =  articleRepository.findAll(exampleArticle)

        val firstArticle = retrivedData.first { it.articleText!=null }
        if (firstArticle == null) {throw DataNotFoundException()}

        val sort:Sort
        if (firstArticle.publicationTS != null){
            sort = Sort.by(Sort.Direction.DESC,"publicationTS")
        } else if(firstArticle.modificationTS !=null){
            sort = Sort.by(Sort.Direction.DESC,"modificationTS")
        }else{
            sort = Sort.by(Sort.Direction.DESC,"modified")
        }

        var articleSequence
                =  articleRepository.findAll(exampleArticle,sort).asSequence().filter { it.articleText !=null }

        if (lastArticleId!=null){
            articleSequence = articleSequence
                                .dropWhile { it.id != lastArticleId }
                                .drop(1)
        }

        val articles = mutableListOf<Article>()
        articleSequence
                .take(pageSize)
                .toCollection(articles)

        if (articles.size == 0) {throw DataNotFoundException()}

        return articles
    }

    fun getArticlesForPageAfterLast(pageId: String, pageSize: Int, lastArticleId: String): List<Article> {
        return getLatestArticlesForPage(pageId,pageSize,lastArticleId)
    }
}