package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.database.Page
import com.dasbikash.news_server_data_coordinator_rest.services.PageService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("pages")
class PageController (@Autowired val pageService: PageService){

    @GetMapping(value = arrayOf("","/"))
    fun getAllActivePages():ResponseEntity<List<Page>>{
        return RestControllerUtills.listEntityToResponseEntity(
                pageService.getAllActivePages()
        )
    }

    @GetMapping("/newspaper-id/{newsPaperId}/top-level-pages")
    fun getAllTopLevelPagesForNewsPaper(@PathVariable("newsPaperId") newsPaperId:String):ResponseEntity<List<Page>>{
        return RestControllerUtills.listEntityToResponseEntity(
                pageService.getAllTopLevelPagesForNewsPaper(newsPaperId)
        )
    }

    @GetMapping("/top-level-page-id/{pageId}")
    fun getAllChildPagesForTopLevelPage(@PathVariable("pageId") pageId:String):ResponseEntity<List<Page>>{
        return RestControllerUtills.listEntityToResponseEntity(
                pageService.getAllChildPagesForTopLevelPage(pageId)
        )
    }
}