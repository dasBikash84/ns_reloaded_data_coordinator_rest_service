package com.dasbikash.news_server_data_coordinator_rest.utills

import com.dasbikash.news_server_data_coordinator_rest.exceptions.DataNotFoundException
import com.dasbikash.news_server_data_coordinator_rest.model.database.DataCoordinatorRestEntity
import org.springframework.http.ResponseEntity

object RestControllerUtills {
    fun <T : DataCoordinatorRestEntity> listEntityToResponseEntity(entityList: List<T>): ResponseEntity<List<T>> {
        if (entityList.isEmpty()) {
            throw DataNotFoundException()
        }
        return ResponseEntity.ok(entityList)
    }

    fun <T : DataCoordinatorRestEntity> entityToResponseEntity(entity: T?): ResponseEntity<T> {
        if (entity == null) {
            throw DataNotFoundException()
        }
        return ResponseEntity.ok(entity)
    }
}