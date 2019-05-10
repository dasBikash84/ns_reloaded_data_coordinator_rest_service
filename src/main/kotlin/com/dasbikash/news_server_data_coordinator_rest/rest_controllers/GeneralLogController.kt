package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.GeneralLog
import com.dasbikash.news_server_data_coordinator_rest.services.GeneralLogService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("general-logs")
class GeneralLogController @Autowired
constructor(val generalLogService: GeneralLogService) {

    @Value("\${log.default_page_size}")
    var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    var maxPageSize: Int = 50

    @GetMapping("")
    fun getLatestGeneralLogs(@RequestParam("page-size") pageSizeRequest:Int?): ResponseEntity<List<GeneralLog>> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return RestControllerUtills.listEntityToResponseEntity(generalLogService.getLatestGeneralLogs(pageSize))
    }

    @GetMapping("/before/general-log-id/{log-id}")
    fun getGeneralLogsBeforeGivenId(@RequestParam("page-size") pageSizeRequest:Int?,
                                        @PathVariable("log-id") lastGeneralLogId:Int)
            : ResponseEntity<List<GeneralLog>> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return RestControllerUtills.listEntityToResponseEntity(generalLogService.getGeneralLogsBeforeGivenId(lastGeneralLogId,pageSize))
    }
}
