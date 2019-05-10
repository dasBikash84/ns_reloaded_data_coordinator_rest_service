package com.dasbikash.news_server_data_coordinator_rest.rest_controllers

import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.SettingsUpdateLog
import com.dasbikash.news_server_data_coordinator_rest.services.SettingsUpdateLogService
import com.dasbikash.news_server_data_coordinator_rest.utills.RestControllerUtills
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("settings-update-logs")
class SettingsUpdateLogController @Autowired
constructor(val settingsUpdateLogService: SettingsUpdateLogService) {

    @Value("\${log.default_page_size}")
    var defaultPageSize: Int = 10

    @Value("\${log.max_page_size}")
    var maxPageSize: Int = 50

    @GetMapping("")
    fun getLatestSettingsUpdateLogs(@RequestParam("page-size") pageSizeRequest:Int?): ResponseEntity<List<SettingsUpdateLog>> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return RestControllerUtills.listEntityToResponseEntity(settingsUpdateLogService.getLatestSettingsUpdateLogs(pageSize))
    }

    @GetMapping("/before/settings-update-log-id/{log-id}")
    fun getSettingsUpdateLogsBeforeGivenId(@RequestParam("page-size") pageSizeRequest:Int?,
                                        @PathVariable("log-id") lastErrorLogId:Int)
            : ResponseEntity<List<SettingsUpdateLog>> {
        var pageSize = defaultPageSize
        pageSizeRequest?.let {
            when{
                it>=maxPageSize -> pageSize = maxPageSize
                it>0            -> pageSize = it
            }
        }
        return RestControllerUtills.listEntityToResponseEntity(settingsUpdateLogService.getSettingsUpdateLogsBeforeGivenId(lastErrorLogId,pageSize))
    }
}
