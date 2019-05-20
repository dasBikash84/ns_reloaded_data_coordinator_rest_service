package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.Countries
import com.dasbikash.news_server_data_coordinator_rest.services.CountryService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("countries",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class CountryController @Autowired
constructor(open var countryService: CountryService,
            open var restControllerUtills: RestControllerUtills) {

    @GetMapping(value = arrayOf("","/"),produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getAllCountriesEndPoint(@Autowired request: HttpServletRequest):ResponseEntity<Countries>{
        return restControllerUtills.entityToResponseEntity(Countries(countryService.getAllCountries()))
    }

}
