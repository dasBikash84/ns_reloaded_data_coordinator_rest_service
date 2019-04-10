package com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions

class NewsPaperNotFoundByIdException : DataNotFoundException {
    constructor(newspaperId: String) : super("Newspaper with id: ${newspaperId} was not found.")
}