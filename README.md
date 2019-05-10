## REST service (developed with Spring MVC) for monitoring and control of [Data Coordinator App](https://github.com/dasBikash84/news_server_data_coordinator) 

### Technologies used are as below:
* Language: `Kotlin`.
* Database: `MySQL`
* Database access Api: `Spring JPA`
* REST framework: `Spring MVC` and `Spring Boot`.

### This rest service supports following operations. Details are [here](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/REST_END_POINT%20details.md):

* Get and Delete for [`General Log`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/database/log_entities/GeneralLog.kt) entries.
* Get and Delete for [`Error Log`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/database/log_entities/ErrorLog.kt) entries.
* Get and Delete for [`Article Download Log`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/database/log_entities/ArticleDownloadLog.kt) entries.
* Get and Delete for [`Article Upload Log`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/database/log_entities/ArticleUploadLog.kt) entries.
* Get and Delete for [`Settings Update Log`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/database/log_entities/SettingsUpdateLog.kt) entries.
* Get and Delete for [`Settings Upload Log`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/database/log_entities/SettingsUploadLog.kt) entries.
* Get and Post for [`Article Uploader Status Change Log`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/database/log_entities/ArticleUploaderStatusChangeLog.kt) entries.

##### All `Delete` and `Post` operations has to be performed via *OTP*. Details are [here](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/REST_END_POINT%20details.md)

### Beside that it can also act as an back-up data source for Newspaper Settings/Data and supports following end points:
* Get for [`Country`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/database/Country.kt) entries.
* Get for [`Language`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/database/Language.kt) entries.
* Get for [`Newspaper`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/database/Newspaper.kt) entries.
* Get for [`Page`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/database/Page.kt) entries.
* Get for [`Article`](https://github.com/dasBikash84/ns_reloaded_data_coordinator_rest_service/blob/master/src/main/kotlin/com/dasbikash/news_server_data_coordinator_rest/model/database/Article.kt) data.
