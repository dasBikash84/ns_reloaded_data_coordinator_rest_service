package com.dasbikash.news_server_spring_mvc_rest_datasource.repositories

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Newspaper
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Page
import org.springframework.data.jpa.repository.JpaRepository

interface PageRepository : JpaRepository<Page, String>{

    companion object {
        @JvmStatic val TOP_LEVEL_PAGE_PARENT_ID = "PAGE_ID_0"
    }

    //Find active top level pages for a newspaper
    fun findPagesByNewspaperAndParentPageIdAndActive(newspaper: Newspaper,
                                                     parentPageId: String= TOP_LEVEL_PAGE_PARENT_ID,
                                                     active:Boolean=true):List<Page>

    fun findPagesByParentPageIdAndActive(parentPageId: String,active: Boolean=true):List<Page>

    fun findAllByActive(active: Boolean = true): List<Page>
}