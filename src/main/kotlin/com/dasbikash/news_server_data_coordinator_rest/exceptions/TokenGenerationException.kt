package com.dasbikash.news_server_data_coordinator_rest.exceptions

open class TokenGenerationException : InternalError {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}