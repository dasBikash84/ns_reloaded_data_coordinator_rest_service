package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.Newspapers
import com.dasbikash.news_server_data_coordinator_rest.model.database.Newspaper
import com.dasbikash.news_server_data_coordinator_rest.services.NewsPaperService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("newspapers")
class NewsPaperController @Autowired
constructor(private val newsPaperService: NewsPaperService,
            val restControllerUtills: RestControllerUtills) {

    @GetMapping(value = arrayOf("","/"))
    fun getAllActiveNewsPapers():ResponseEntity<Newspapers>{
        return restControllerUtills.entityToResponseEntity(Newspapers(
                newsPaperService.getAllActiveNewsPapers()))
    }

    @GetMapping("/country-name/{countryName}")
    fun getNewPaperByCountryName(@PathVariable("countryName") countryName:String):ResponseEntity<Newspapers>{
        return restControllerUtills.entityToResponseEntity(Newspapers(
                newsPaperService.getAllNewPaperByCountryName(countryName)))
    }

    @GetMapping("/language-id/{languageId}")
    fun getNewPaperByLanguageId(@PathVariable("languageId") languageId:String):ResponseEntity<Newspapers>{
        return restControllerUtills.entityToResponseEntity(Newspapers(
                        newsPaperService.getAllNewPaperByLanguageId(languageId)))
    }
}
