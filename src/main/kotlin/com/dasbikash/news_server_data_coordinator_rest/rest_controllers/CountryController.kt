package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.database.Country
import com.dasbikash.news_server_data_coordinator_rest.services.CountryService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("countries")
class CountryController @Autowired
constructor(private val countryService: CountryService,
            val restControllerUtills: RestControllerUtills) {

    @GetMapping(value = arrayOf("","/"))
    fun getAllActiveNewsPapers():ResponseEntity<List<Country>>{
        return restControllerUtills.listEntityToResponseEntity(countryService.getAllCountries())
    }

}
