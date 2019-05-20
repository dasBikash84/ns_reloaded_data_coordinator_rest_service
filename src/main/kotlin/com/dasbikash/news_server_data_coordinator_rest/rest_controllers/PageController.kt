package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.Pages
import com.dasbikash.news_server_data_coordinator_rest.services.PageService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("pages",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
open class PageController (open var pageService: PageService,
                           open var restControllerUtills: RestControllerUtills){

    @GetMapping(value = arrayOf("","/"),produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getAllActivePagesEndPoint(@Autowired request: HttpServletRequest):ResponseEntity<Pages>{
        return restControllerUtills.entityToResponseEntity(
                        Pages(pageService.getAllActivePages()))
    }

    @GetMapping("/newspaper-id/{newsPaperId}/top-level-pages",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getAllTopLevelPagesForNewsPaperEndPoint(@PathVariable("newsPaperId") newsPaperId:String,
                                                     @Autowired request: HttpServletRequest)
            :ResponseEntity<Pages>{
        return restControllerUtills.entityToResponseEntity(
                        Pages(pageService.getAllTopLevelPagesForNewsPaper(newsPaperId)))
    }

    @GetMapping("/top-level-page-id/{pageId}",produces = arrayOf(MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE))
    open fun getAllChildPagesForTopLevelPageEndPoint(@PathVariable("pageId") pageId:String,
                                                     @Autowired request: HttpServletRequest)
            :ResponseEntity<Pages>{
        return restControllerUtills.entityToResponseEntity(
                        Pages(pageService.getAllChildPagesForTopLevelPage(pageId)))
    }
}