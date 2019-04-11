package com.dasbikash.news_server_spring_mvc_rest_datasource.advice

import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.CustomDataAccessException
import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.DataNotFoundException
import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.NewsPaperNotFoundByIdException
import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.NewsPaperNotFoundByNameException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class NewsPaperNotFoundAdvice {

    @ExceptionHandler(NewsPaperNotFoundByIdException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun newsPaperNotFoundByIdHandler(ex:NewsPaperNotFoundByIdException):String{
        return ex.message ?: ""
    }

    @ExceptionHandler(NewsPaperNotFoundByNameException::class)
    fun newsPaperNotFoundByIdHandler(ex:NewsPaperNotFoundByNameException):ResponseEntity<Unit>{
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler(CustomDataAccessException::class)
    fun dataAccessExceptionHandler(ex:CustomDataAccessException):ResponseEntity<Unit>{
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler(DataNotFoundException::class)
    fun dataAccessExceptionHandler(ex:DataNotFoundException):ResponseEntity<Unit>{
        return ResponseEntity.notFound().build()
    }

    /*@ExceptionHandler(Throwable::class)
    fun genExceptionHandler(ex:Throwable):ResponseEntity<Unit>{
        return ResponseEntity.notFound().build()
    }*/
}