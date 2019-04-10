package com.dasbikash.news_server_spring_mvc_rest_datasource.rest_controllers

import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.CustomDataNotFoundException
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Country
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Language
import com.dasbikash.news_server_spring_mvc_rest_datasource.services.LanguageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("language")
class LanguageController @Autowired
constructor(private val languageService: LanguageService) {

    @GetMapping("")
    fun getAllActiveNewsPapers():ResponseEntity<List<Language>>{
        val languages = listOf<Language>()//languageService.getAllLanguages()
        if (languages.size == 0){
            throw CustomDataNotFoundException()
        }
        return ResponseEntity.ok(languages)
    }

}
