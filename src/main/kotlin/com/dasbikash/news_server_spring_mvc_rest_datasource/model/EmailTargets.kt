package com.dasbikash.news_server_spring_mvc_rest_datasource.model

data class EmailTargets (
    var toAddresses:String? = null,
    var ccAddresses:String? = null,
    var bccAddresses:String? = null
)