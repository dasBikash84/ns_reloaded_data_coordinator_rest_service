package com.dasbikash.news_server_spring_mvc_rest_datasource.utills

import com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions.CustomDataNotFoundException
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.NsSpringRestDbEntity
import org.springframework.http.ResponseEntity

object RestControllerUtills {
    fun <T : NsSpringRestDbEntity> listEntityToResponseEntity(entiryList: List<T>): ResponseEntity<List<T>> {
        if (entiryList.isEmpty()) {
            throw CustomDataNotFoundException()
        }
        return ResponseEntity.ok(entiryList)
    }

    fun <T : NsSpringRestDbEntity> entityToResponseEntity(entity: T?): ResponseEntity<T> {
        if (entity == null) {
            throw CustomDataNotFoundException()
        }
        return ResponseEntity.ok(entity)
    }
}