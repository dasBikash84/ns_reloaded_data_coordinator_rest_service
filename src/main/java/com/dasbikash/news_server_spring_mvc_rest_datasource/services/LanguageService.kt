package com.dasbikash.news_server_spring_mvc_rest_datasource.services

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Language
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.LanguageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LanguageService(@Autowired val languageRepository: LanguageRepository) {
    fun getAllLanguages(): List<Language> {
        return languageRepository.findAll()
    }
}
