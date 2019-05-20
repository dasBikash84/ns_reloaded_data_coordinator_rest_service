package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.Articles
import com.dasbikash.news_server_data_coordinator_rest.model.database.Article
import com.dasbikash.news_server_data_coordinator_rest.services.ArticleService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest



@RestController
@RequestMapping("articles",produces = arrayOf(MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE))
open class ArticleController constructor(open var articleService: ArticleService,
                                         open var restControllerUtills: RestControllerUtills) {

    @Value("\${article.default_page_size}")
    open var defaultPageSize: Int = 5

    @Value("\${article.max_page_size}")
    open var maxPageSize: Int = 10

    @GetMapping("/page-id/{pageId}",produces = arrayOf(MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE))
    open fun getLatestArticlesByPageIdEndPoint(@PathVariable pageId: String,
                                               @RequestParam("article_count") articleCount:Int?,
                                               @RequestHeader headers: HttpHeaders,
                                               @Autowired request: HttpServletRequest): ResponseEntity<Articles> {

        var pageSize = defaultPageSize

        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it

            }
        }
        return restControllerUtills.entityToResponseEntity(Articles(articleService.getLatestArticlesByPageId(pageId,pageSize)))
    }

    @GetMapping("/page-id/{pageId}/last-article-id/{lastArticleId}",
                        produces = arrayOf(MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE))
    open fun getArticlesForPageBeforeArticleIdEndPoint(@PathVariable pageId: String,
                                                 @PathVariable lastArticleId: String,
                                                 @RequestParam("article_count") articleCount:Int?,
                                                 @Autowired request: HttpServletRequest): ResponseEntity<Articles> {

        var pageSize = defaultPageSize

        articleCount?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0 -> pageSize = it
            }
        }
        return restControllerUtills.entityToResponseEntity(Articles(articleService.getArticlesForPageBeforeArticleId(pageId,pageSize,lastArticleId)))
    }

    @GetMapping("/top-level-page-id/{topLevelPageId}/latest-article",
            produces = arrayOf(MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE))
    open fun getLatestArticleForTopLevelPageEndPoint(@PathVariable topLevelPageId:String,
                                                @Autowired request: HttpServletRequest):ResponseEntity<Article>{
        return restControllerUtills.entityToResponseEntity(articleService.getLatestArticleForTopLevelPage(topLevelPageId))
    }
}
