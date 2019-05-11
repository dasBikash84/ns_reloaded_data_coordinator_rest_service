### REST service End point details:

#### [`General Log`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/rest_controllers/GeneralLogController.kt) endpoints:

<a name="latest_log"></a>  
* For latest *General Log* entries:
   * Type: `GET`
   * Path: http://localhost:8099/general-logs
   * Path Param: page-size(result-count) | optional | Default 10 | Max 50
   * Response: A list of latest *General Log* entries if any or [`HttpStatus.NOT_FOUND`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpStatus.html#NOT_FOUND).
<a name="log_before_given_id"></a>    
* For *General Log* entries Before Given Id:
  * Type: `GET`
  * Path: http://localhost:8099/general-logs/before/general-log-id/{log-id}
  * Path Variable: *log-id* (id of last *General Log* entry)
  * Path Param: page-size(result-count) | optional | Default 10 | Max 50
  * Response: A list of latest *General Log* entries if any or `HttpStatus.NOT_FOUND` for invalid *log-id*.
  
<a name="request_log_delete_token_generation"></a>   
* For *General Log* entries deletion token generation request:
  * Type: `DELETE`
  * Path: http://localhost:8099/general-logs/request_log_delete_token_generation
  * Response: [`LogEntryDeleteRequestFormat`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/LogEntryDeleteRequest.kt) 
  (and generated token will be emailed to authorized email addresses) or 
  [`HttpStatus.INTERNAL_SERVER_ERROR`](https://docs.spring.io/spring-framework/docs/curhttps://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpStatus.html#INTERNAL_SERVER_ERRORrent/javadoc-api/org/springframework/http/HttpStatus.html#INTERNAL_SERVER_ERROR) for failure.
<a name="log_delete_request"></a>   
* For *General Log* entries deletion:
  * Type: `DELETE`
  * Path: http://localhost:8099/general-logs
  * RequestBody : [`LogEntryDeleteRequest`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/LogEntryDeleteRequest.kt)
  * Response: List of deleted *General Log* entries or 
  [`HttpStatus.BAD_REQUEST`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpStatus.html#BAD_REQUEST) for invalid `LogEntryDeleteRequest`.
  
#### [`Error Log`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/database/log_entities/ErrorLog.kt) endpoints:
* For latest *Error Log* entries:
    * Type: `GET`
    * Path: http://localhost:8099/error-logs
    * Path Param & Response: Format same as [`General Log`](#latest_log) but for `Error Log`
     
* For *Error Log* entries Before Given Id:
    * Type: `GET`
    * Path: http://localhost:8099/error-logs/before/error-log-id/{log-id}
    * Path Variable, Path Param & Response: Format same as [`General Log`](#log_before_given_id) but for `Error Log`.  
    
* For *Error Log* entries deletion token generation request:
    * Type: `DELETE`
    * Path: http://localhost:8099/error-logs/request_log_delete_token_generation
    * Response: Format same as [`General Log`](#request_log_delete_token_generation) but for `Error Log`. 
    
* For *Error Log* entries deletion:
    * Type: `DELETE`
    * Path: http://localhost:8099/error-logs 
    * Response: Format same as [`General Log`](#log_delete_request) but for `Error Log`. 