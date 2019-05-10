package com.dasbikash.news_server_data_coordinator_rest.services

import com.dasbikash.news_server_data_coordinator_rest.model.database.Country
import com.dasbikash.news_server_data_coordinator_rest.repositories.CountryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CountryService @Autowired
constructor(val countryRepository: CountryRepository){

    fun getAllCountries():List<Country>{
        return countryRepository.findAll()
    }
}