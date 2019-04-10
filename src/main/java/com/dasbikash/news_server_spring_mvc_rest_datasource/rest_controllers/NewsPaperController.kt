package com.dasbikash.news_server_spring_mvc_rest_datasource.rest_controllers

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Newspaper
import com.dasbikash.news_server_spring_mvc_rest_datasource.services.NewsPaperService
import com.dasbikash.news_server_spring_mvc_rest_datasource.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("newspapers")
class NewsPaperController @Autowired
constructor(private val newsPaperService: NewsPaperService) {

    @GetMapping(value = arrayOf("","/"))
    fun getAllActiveNewsPapers():ResponseEntity<List<Newspaper>>{
        return RestControllerUtills.listEntityToResponseEntity(newsPaperService.getAllActiveNewsPapers())
    }

    /*@GetMapping("/all")
    fun getAllNewsPapers():ResponseEntity<List<Newspaper>>{
        return RestControllerUtills.listEntityToResponseEntity(newsPaperService.getAllNewsPapers())
    }

    @GetMapping("/id/{id}")
    fun getNewPaperById(@PathVariable("id") id:String):ResponseEntity<Newspaper>{
        return RestControllerUtills.entityToResponseEntity(newsPaperService.getNewPaperById(id))
    }*/

    @GetMapping("/country-name/{countryName}")
    fun getNewPaperByCountryName(@PathVariable("countryName") countryName:String):ResponseEntity<List<Newspaper>>{
        return RestControllerUtills.listEntityToResponseEntity(newsPaperService.getAllNewPaperByCountryName(countryName))
    }

    @GetMapping("/language-id/{languageId}")
    fun getNewPaperByLanguageId(@PathVariable("languageId") languageId:String):ResponseEntity<List<Newspaper>>{
        return RestControllerUtills.listEntityToResponseEntity(newsPaperService.getAllNewPaperByLanguageId(languageId))
    }
}
