package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.PageGroups
import com.dasbikash.news_server_data_coordinator_rest.model.Pages
import com.dasbikash.news_server_data_coordinator_rest.model.database.Page
import com.dasbikash.news_server_data_coordinator_rest.services.PageGroupService
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
@RequestMapping("page-groups")
open class PageGroupController
constructor(open var pageGroupService: PageGroupService,
            open var restControllerUtills: RestControllerUtills){

    @GetMapping(value = arrayOf("","/"))
    open fun getAllActivePagesEndPoint(@Autowired request: HttpServletRequest):ResponseEntity<PageGroups>{
        return restControllerUtills.entityToResponseEntity(pageGroupService.getPageGroups())
    }
}