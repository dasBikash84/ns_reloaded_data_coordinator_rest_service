package com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions

class NewsPaperNotFoundByNameException(newsPaperName: String) :
        CustomDataNotFoundException("Newspaper with name: ${newsPaperName} was not found.")
