package com.dasbikash.news_server_data_coordinator_rest.services

import com.dasbikash.news_server_data_coordinator_rest.model.database.Language
import com.dasbikash.news_server_data_coordinator_rest.repositories.LanguageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class LanguageService(open var languageRepository: LanguageRepository) {

    fun getAllLanguages(): List<Language> {
        return languageRepository.findAll()
    }
}
