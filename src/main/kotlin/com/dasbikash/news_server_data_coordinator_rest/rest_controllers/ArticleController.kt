package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.Articles
import com.dasbikash.news_server_data_coordinator_rest.model.database.Article
import com.dasbikash.news_server_data_coordinator_rest.services.ArticleService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("articles")
class ArticleController @Autowired
constructor(val articleService: ArticleService,
            val restControllerUtills: RestControllerUtills) {

    @Value("\${article.default_page_size}")
    var defaultPageSize: Int = 5

    @Value("\${article.max_page_size}")
    var maxPageSize: Int = 10

    @GetMapping("/page-id/{pageId}")
    fun getLatestArticlesByPageId(@PathVariable pageId: String, @RequestParam("article_count") articleCount:Int?): ResponseEntity<Articles> {

        var pageSize = defaultPageSize

        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(Articles(articleService.getArticlesByPageId(pageId,pageSize)))
    }

    @GetMapping("/page-id/{pageId}/last-article-id/{lastArticleId}")
    fun getArticlesForPageAfterArticle
            (@PathVariable pageId: String,@PathVariable lastArticleId: String,@RequestParam("article_count") articleCount:Int?): ResponseEntity<Articles> {

        var pageSize = defaultPageSize

        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(Articles(articleService.getArticlesForPageAfterLast(pageId,pageSize,lastArticleId)))
    }

    @GetMapping("/top-level-page-id/{topLevelPageId}/latest-article")
    fun getLatestArticleForTopLevelPage(@PathVariable topLevelPageId:String):ResponseEntity<Article>{
        return restControllerUtills.entityToResponseEntity(articleService.getLatestArticleForTopLevelPage(topLevelPageId))
    }
}
