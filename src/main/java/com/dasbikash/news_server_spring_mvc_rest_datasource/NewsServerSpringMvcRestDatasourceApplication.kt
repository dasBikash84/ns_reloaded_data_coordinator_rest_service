package com.dasbikash.news_server_spring_mvc_rest_datasource

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Newspaper
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Page
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.ArticleRepository
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.NewspaperRepository
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.PageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class NewsServerSpringMvcRestDatasourceApplication : CommandLineRunner {

    @Throws(Exception::class)
    override fun run(vararg args: String) {
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(NewsServerSpringMvcRestDatasourceApplication::class.java, *args)
        }
    }
}
