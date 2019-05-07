package com.dasbikash.news_server_spring_mvc_rest_datasource.model

data class EmailAuth (
    var userName:String? = null,
    var passWord:String? = null,
    var properties:Map<String,String>?=null
)