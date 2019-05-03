package com.dasbikash.news_server_spring_mvc_rest_datasource.services

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Newspaper
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Page
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.NewspaperRepository
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.PageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PageService @Autowired
constructor( val pageRepository: PageRepository,val newspaperRepository: NewspaperRepository) {

    fun getAllActivePages(): List<Page> {
        val allPages= mutableListOf<Page>()
        pageRepository
            .findAllByActive()
            .asSequence()
            .map {
                if (it.topLevelPage!!){
                    if (pageRepository.findPagesByParentPageIdAndActiveOrderByIdAsc(it.id).size>0){
                        it.hasChild = true
                    }
                }
                it
            }
            .toCollection(allPages)
        return allPages;
    }

    private fun findTopLevelPagesForNewsPaper(newspaper: Newspaper):List<Page>{
        return pageRepository.findPagesByNewspaperAndParentPageIdAndActive(newspaper)
    }

    fun getAllTopLevelPagesForNewsPaper(newsPaperId: String): List<Page> {

        val allPages= mutableListOf<Page>()

        val newspaperOptional = newspaperRepository.findById(newsPaperId)

        if (newspaperOptional.isPresent){
            findTopLevelPagesForNewsPaper(newspaperOptional.get())
                .asSequence()
                .map {
                    if (pageRepository.findPagesByParentPageIdAndActiveOrderByIdAsc(it.id).size>0){
                        it.hasChild = true
                    }
                it
                }
                .toCollection(allPages)

        }
        return allPages;
    }

    fun getAllChildPagesForTopLevelPage(pageId: String): List<Page> {
        return pageRepository.findPagesByParentPageIdAndActiveOrderByIdAsc(pageId)
    }
}