package com.dasbikash.news_server_data_coordinator_rest.model

data class EmailTargets (
    var toAddresses:String? = null,
    var ccAddresses:String? = null,
    var bccAddresses:String? = null
)