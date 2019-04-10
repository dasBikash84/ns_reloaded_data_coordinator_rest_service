package com.dasbikash.news_server_spring_mvc_rest_datasource.services

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Country
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Newspaper
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.CountryRepository
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.NewspaperRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CountryService @Autowired
constructor(val countryRepository: CountryRepository){

    fun getAllCountries():List<Country>{
        return countryRepository.findAll()
    }
}