package com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities

import com.dasbikash.news_server_data_coordinator_rest.model.database.DatabaseTableNames
import org.aspectj.lang.JoinPoint
import javax.persistence.*
import javax.servlet.http.HttpServletRequest

@Entity
@Table(name = DatabaseTableNames.REST_ACTIVITY_LOG_TABLE_NAME)
class RestActivityLog(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id:Int?=null,
        val requestURL:String,
        val requestMethod:String,
        val remoteHost:String,
        val methodSignature:String,
        val timeTakenMs:Int,
        val returnedEntiryCount:Int?=null,
        val exceptionClassName:String?=null,
        val acceptHeader:String?=null,
        val userAgentHeader:String?=null
){
    companion object{
        fun getInstance(joinPoint: JoinPoint, request: HttpServletRequest, timeTakenMs: Int,
                        exceptionClassFullName: String?=null, returnedEntiryCount:Int?=null,
                        acceptHeader: String?=null,userAgentHeader: String?=null)
                :RestActivityLog{
            return RestActivityLog(requestURL = request.requestURL.toString(),methodSignature = joinPoint.signature.toString(),
                    requestMethod = request.method,remoteHost = request.remoteHost,timeTakenMs = timeTakenMs,
                    exceptionClassName = exceptionClassFullName,returnedEntiryCount = returnedEntiryCount,
                    acceptHeader = acceptHeader,userAgentHeader = userAgentHeader)
        }
    }
}