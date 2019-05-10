package com.dasbikash.news_server_data_coordinator_rest.model

import com.dasbikash.news_server_data_coordinator_rest.model.database.*
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.*

class ArticleDownloadLogs(
        val articleDownloadLogs:List<ArticleDownloadLog>
): DataCoordinatorRestEntity

class ArticleUploaderStatusChangeLogs(
        val articleUploaderStatusChangeLogs:List<ArticleUploaderStatusChangeLog>
): DataCoordinatorRestEntity

class ArticleUploadLogs(
        val articleUploadLogs:List<ArticleUploadLog>
): DataCoordinatorRestEntity

class ErrorLogs(
        val errorLogs:List<ErrorLog>
): DataCoordinatorRestEntity

class GeneralLogs(
        val generalLogs:List<GeneralLog>
): DataCoordinatorRestEntity

class SettingsUpdateLogs(
        val settingsUpdateLogs:List<SettingsUpdateLog>
): DataCoordinatorRestEntity

class SettingsUploadLogs(
        val settingsUploadLogs:List<SettingsUploadLog>
): DataCoordinatorRestEntity

class Articles(
        val articles:List<Article>
): DataCoordinatorRestEntity

class Countries(
        val countries:List<Country>
): DataCoordinatorRestEntity

class Languages(
        val languages:List<Language>
): DataCoordinatorRestEntity

class Newspapers(
        val newspapers:List<Newspaper>
): DataCoordinatorRestEntity

class Pages(
        val pages:List<Page>
): DataCoordinatorRestEntity