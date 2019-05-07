package com.dasbikash.news_server_spring_mvc_rest_datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources(
        {
                @PropertySource("classpath:application.properties"),
                @PropertySource("classpath:spring_mvc_rest.properties")
        }
    )
public class NewsServerDataCoordinatorRestApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                SpringApplication.run(NewsServerDataCoordinatorRestApplication.class, args);
    }
}
