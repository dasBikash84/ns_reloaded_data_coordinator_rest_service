package com.dasbikash.news_server_data_coordinator_rest.model

import com.dasbikash.news_server_data_coordinator_rest.model.database.*
import com.dasbikash.news_server_data_coordinator_rest.model.database.log_entities.*

class ArticleDownloadLogs(
        val articleDownloadLogs:List<ArticleDownloadLog>
): DataCoordinatorRestEntity,OutputWrapper{
    override fun getOutPutCount(): Int {
        return articleDownloadLogs.size
    }
}

class ArticleUploaderStatusChangeLogs(
        val articleUploaderStatusChangeLogs:List<ArticleUploaderStatusChangeLog>
): DataCoordinatorRestEntity,OutputWrapper{
    override fun getOutPutCount(): Int {
        return articleUploaderStatusChangeLogs.size
    }
}

class ArticleUploadLogs(
        val articleUploadLogs:List<ArticleUploadLog>
): DataCoordinatorRestEntity,OutputWrapper{
    override fun getOutPutCount(): Int {
        return articleUploadLogs.size
    }
}

class ErrorLogs(
        val errorLogs:List<ErrorLog>
): DataCoordinatorRestEntity,OutputWrapper{
    override fun getOutPutCount(): Int {
        return errorLogs.size
    }
}

class GeneralLogs(
        val generalLogs:List<GeneralLog>
): DataCoordinatorRestEntity,OutputWrapper{
    override fun getOutPutCount(): Int {
        return generalLogs.size
    }
}

class SettingsUpdateLogs(
        val settingsUpdateLogs:List<SettingsUpdateLog>
): DataCoordinatorRestEntity,OutputWrapper{
    override fun getOutPutCount(): Int {
        return settingsUpdateLogs.size
    }
}

class SettingsUploadLogs(
        val settingsUploadLogs:List<SettingsUploadLog>
): DataCoordinatorRestEntity,OutputWrapper{
    override fun getOutPutCount(): Int {
        return settingsUploadLogs.size
    }
}

class Articles(
        val articles:List<Article>
): DataCoordinatorRestEntity,OutputWrapper{
    override fun getOutPutCount(): Int {
        return articles.size
    }
}

class Countries(
        val countries:List<Country>
): DataCoordinatorRestEntity,OutputWrapper{
    override fun getOutPutCount(): Int {
        return countries.size
    }
}

class Languages(
        val languages:List<Language>
): DataCoordinatorRestEntity,OutputWrapper{
    override fun getOutPutCount(): Int {
        return languages.size
    }
}

class Newspapers(
        val newspapers:List<Newspaper>
): DataCoordinatorRestEntity,OutputWrapper{
    override fun getOutPutCount(): Int {
        return newspapers.size
    }
}

class Pages(
        val pages:List<Page>
): DataCoordinatorRestEntity,OutputWrapper{
    override fun getOutPutCount(): Int {
        return pages.size
    }
}

class PageGroups(
        val pageGroupMap:Map<String,PageGroup>
): DataCoordinatorRestEntity,OutputWrapper{
    override fun getOutPutCount(): Int {
        return pageGroupMap.values.size
    }
}

interface OutputWrapper{
    fun getOutPutCount():Int
}