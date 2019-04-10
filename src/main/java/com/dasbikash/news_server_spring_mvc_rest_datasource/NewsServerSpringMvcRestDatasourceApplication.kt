package com.dasbikash.news_server_spring_mvc_rest_datasource

import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.ArticleRepository
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.PageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

@SpringBootApplication
@PropertySources(
        PropertySource("classpath:application.properties"),
        PropertySource("classpath:spring_mvc_rest.properties")
)
open class NewsServerSpringMvcRestDatasourceApplication : CommandLineRunner {

    val pageRepository: PageRepository
    val articleRepository: ArticleRepository

    @Autowired
    constructor(pageRepository: PageRepository, articleRepository: ArticleRepository) {
        this.pageRepository = pageRepository
        this.articleRepository = articleRepository
    }


    @Throws(Exception::class)
    override fun run(vararg args: String) {

       /* val pageOptional = pageRepository.findById("PAGE_ID_479")

        if (!pageOptional.isPresent){
            throw DataNotFoundException()
        }

        val page = pageOptional.get()

        println(page.name)

        val article = Article()
        article.page = page
        val exampleArticle = org.springframework.data.domain.Example.of(article)

        var count = 0;

        val articles = mutableListOf<Article>()

        articleRepository.findAll(exampleArticle)
                .asSequence()
                .take(10)
                .forEach {  println("${it.title} : ${it.publicationTS}")  }*/

        /*val pagesOfArticle = articleRepository.findAll(PageRequest.of(3, 10, Sort.by(Sort.Direction.ASC, "modified")));

        *//*println(pagesOfArticle.totalPages)
        println(pagesOfArticle.totalElements)*//*
        pagesOfArticle.get().forEach { println("${it.title} : ${it.publicationTS}") }*/

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(NewsServerSpringMvcRestDatasourceApplication::class.java, *args)
        }
    }
}
