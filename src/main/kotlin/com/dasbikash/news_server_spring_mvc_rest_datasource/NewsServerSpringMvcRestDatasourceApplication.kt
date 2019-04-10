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
open class NewsServerSpringMvcRestDatasourceApplication{

     companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(NewsServerSpringMvcRestDatasourceApplication::class.java, *args)
        }
    }
}
