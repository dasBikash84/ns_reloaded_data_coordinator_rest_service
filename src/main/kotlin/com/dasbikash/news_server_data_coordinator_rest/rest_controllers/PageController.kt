package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.Pages
import com.dasbikash.news_server_data_coordinator_rest.model.database.Page
import com.dasbikash.news_server_data_coordinator_rest.services.PageService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("pages")
open class PageController (open var pageService: PageService,
                           open var restControllerUtills: RestControllerUtills){

    @GetMapping(value = arrayOf("","/"))
    open fun getAllActivePagesEndPoint(@Autowired request: HttpServletRequest):ResponseEntity<Pages>{
        return restControllerUtills.entityToResponseEntity(
                        Pages(pageService.getAllActivePages()))
    }

    @GetMapping("/newspaper-id/{newsPaperId}/top-level-pages")
    open fun getAllTopLevelPagesForNewsPaperEndPoint(@PathVariable("newsPaperId") newsPaperId:String,
                                                     @Autowired request: HttpServletRequest)
            :ResponseEntity<Pages>{
        return restControllerUtills.entityToResponseEntity(
                        Pages(pageService.getAllTopLevelPagesForNewsPaper(newsPaperId)))
    }

    @GetMapping("/top-level-page-id/{pageId}")
    open fun getAllChildPagesForTopLevelPageEndPoint(@PathVariable("pageId") pageId:String,
                                                     @Autowired request: HttpServletRequest)
            :ResponseEntity<Pages>{
        return restControllerUtills.entityToResponseEntity(
                        Pages(pageService.getAllChildPagesForTopLevelPage(pageId)))
    }
}