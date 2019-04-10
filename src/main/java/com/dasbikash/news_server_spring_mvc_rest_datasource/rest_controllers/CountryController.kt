package com.dasbikash.news_server_spring_mvc_rest_datasource.rest_controllers

import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.CustomDataAccessException
import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.CustomDataNotFoundException
import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.NewsPaperNotFoundByIdException
import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.NewsPaperNotFoundByNameException
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Country
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Newspaper
import com.dasbikash.news_server_spring_mvc_rest_datasource.services.CountryService
import com.dasbikash.news_server_spring_mvc_rest_datasource.services.NewsPaperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("country")
class CountryController @Autowired
constructor(private val countryService: CountryService) {

    @GetMapping("")
    fun getAllActiveNewsPapers():ResponseEntity<List<Country>>{
        val countries = countryService.getAllCountries()
        if (countries.size == 0){
            throw CustomDataNotFoundException()
        }
        return ResponseEntity.ok(countries)
    }

}
