package com.dasbikash.news_server_spring_mvc_rest_datasource.repositories

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Newspaper
import org.springframework.data.jpa.repository.JpaRepository

interface NewspaperRepository : JpaRepository<Newspaper, String>{
    fun findAllByActive(active:Boolean=true):List<Newspaper>
}