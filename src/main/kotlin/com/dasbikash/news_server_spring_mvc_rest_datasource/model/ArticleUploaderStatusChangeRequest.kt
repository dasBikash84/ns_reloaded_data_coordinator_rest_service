package com.dasbikash.news_server_spring_mvc_rest_datasource.model

import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.DataCoordinatorRestEntity
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.ArticleUploadTarget
import com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities.TwoStateStatus

class ArticleUploaderStatusChangeRequest:DataCoordinatorRestEntity {
    val authToken:String? = null
    val articleUploadTarget:ArticleUploadTarget?=null
    val status:TwoStateStatus?=null
}
class ArticleUploaderStatusChangeRequestFormat:DataCoordinatorRestEntity {
    val authToken:String = "Emailed token"
    val articleUploadTarget:String="Target Uploader ID"
    val status:String = "Target Status"
}