package com.dasbikash.news_server_spring_mvc_rest_datasource.exceptions

open class CustomDataNotFoundException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean)
            : super(message, cause, enableSuppression, writableStackTrace)
}