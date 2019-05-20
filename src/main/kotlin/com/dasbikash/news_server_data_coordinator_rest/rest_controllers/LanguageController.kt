package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.Languages
import com.dasbikash.news_server_data_coordinator_rest.model.database.Language
import com.dasbikash.news_server_data_coordinator_rest.services.LanguageService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("languages",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class LanguageController @Autowired
constructor(open var languageService: LanguageService,
            open var restControllerUtills: RestControllerUtills) {

    @GetMapping(value = arrayOf("","/"),produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getAllLanguagesEndPoint(@Autowired request: HttpServletRequest):ResponseEntity<Languages>{
        return restControllerUtills.entityToResponseEntity(Languages(languageService.getAllLanguages()))
    }

}
