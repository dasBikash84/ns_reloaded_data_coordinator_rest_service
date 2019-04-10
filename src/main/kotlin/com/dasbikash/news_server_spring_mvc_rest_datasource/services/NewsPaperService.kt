package com.dasbikash.news_server_spring_mvc_rest_datasource.services

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.Newspaper
import com.dasbikash.news_server_spring_mvc_rest_datasource.repositories.NewspaperRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NewsPaperService @Autowired
constructor(val newspaperRepository:NewspaperRepository){

    fun getAllActiveNewsPapers():List<Newspaper>{
        return newspaperRepository.findAllByActive()
    }

    fun getAllNewsPapers():List<Newspaper>{
        return newspaperRepository.findAll()
    }

    /*fun getNewPaperById(id:String):Newspaper?{
        val optionalNewspaper  = newspaperRepository.findById(id)
        if (optionalNewspaper.isPresent){
            return optionalNewspaper.get()
        }
        return null
    }*/

    fun getAllNewPaperByCountryName(countryName:String):List<Newspaper>{

        val returnNewspaperList = mutableListOf<Newspaper>()

        newspaperRepository
                .findAllByActive()
                .filter { it.country?.name == countryName }
                .toCollection(returnNewspaperList)
        return returnNewspaperList
    }

    fun getAllNewPaperByLanguageId(languageId: String): List<Newspaper> {

        val returnNewspaperList = mutableListOf<Newspaper>()

        newspaperRepository
                .findAllByActive()
                .filter { it.language?.id == languageId }
                .toCollection(returnNewspaperList)
        return returnNewspaperList

    }
}