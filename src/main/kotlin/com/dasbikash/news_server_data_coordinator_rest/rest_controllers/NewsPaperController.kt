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
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("newspapers")
open class NewsPaperController @Autowired
constructor(open var newsPaperService: NewsPaperService,
            open var restControllerUtills: RestControllerUtills) {

    @GetMapping(value = arrayOf("","/"))
    open fun getAllActiveNewsPapersEndPoint(@Autowired request: HttpServletRequest):ResponseEntity<Newspapers>{
        return restControllerUtills.entityToResponseEntity(Newspapers(
                newsPaperService.getAllActiveNewsPapers()))
    }

    @GetMapping("/country-name/{countryName}")
    open fun getNewPaperByCountryNameEndPoint(@PathVariable("countryName") countryName:String,
                                              @Autowired request: HttpServletRequest)
            :ResponseEntity<Newspapers>{
        return restControllerUtills.entityToResponseEntity(Newspapers(
                newsPaperService.getAllNewPaperByCountryName(countryName)))
    }

    @GetMapping("/language-id/{languageId}")
    open fun getNewPaperByLanguageIdEndPoint(@PathVariable("languageId") languageId:String,
                                             @Autowired request: HttpServletRequest)
            :ResponseEntity<Newspapers>{
        return restControllerUtills.entityToResponseEntity(Newspapers(
                        newsPaperService.getAllNewPaperByLanguageId(languageId)))
    }
}
