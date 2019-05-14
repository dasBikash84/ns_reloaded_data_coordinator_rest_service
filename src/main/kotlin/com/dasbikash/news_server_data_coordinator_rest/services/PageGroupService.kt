package com.dasbikash.news_server_data_coordinator_rest.services

import com.dasbikash.news_server_data_coordinator_rest.exceptions.DataNotFoundException
import com.dasbikash.news_server_data_coordinator_rest.model.PageGroups
import com.dasbikash.news_server_data_coordinator_rest.model.database.PageGroup
import com.dasbikash.news_server_data_coordinator_rest.repositories.PageGroupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PageGroupService @Autowired
constructor( val pageGroupRepository: PageGroupRepository) {

    fun getPageGroups(): PageGroups{

        val pageGroupList = pageGroupRepository.findAll()

        if (pageGroupList.size==0){
            throw DataNotFoundException()
        }

        val pageGroupMap = mutableMapOf<String, PageGroup>()
        pageGroupList.asSequence().forEach {
            pageGroupMap.put(it.name!!,it)
        }
        return PageGroups(pageGroupMap)
    }
}