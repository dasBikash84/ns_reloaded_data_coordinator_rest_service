package com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions

class NewsPaperNotFoundByIdException : CustomDataNotFoundException {
    constructor(newspaperId: String) : super("Newspaper with id: ${newspaperId} was not found.")
}