package com.dasbikash.news_server_spring_mvc_rest_datasource.utills

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class LogControllerUtils {

    @Value("\${log.default_page_size}")
    var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    var maxPageSize: Int = 50
}