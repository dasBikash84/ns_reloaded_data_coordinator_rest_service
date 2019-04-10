# REST service developed with Spring MVC(Boot) for News-Server project 

## Technologies used are as below:
* Language used is kotlin.
* Database => MySQL
* Database access Api => Spring JPA
* Developed using Spring MVC and Spring Boot.

## REST service End point details:

### For \"Language data\"

* For all supported language settings:
   * Address: http://localhost:8099/languages
   * Param: N/A
   * Response: A list of language setting entries.
   
### For \"Country data\"

* For all supported Country settings:
   * Address: http://localhost:8099/countries
   * Param: N/A
   * Response: A list of Country setting entries.
   
### For \"Newspaper data\"

* For all supported Newspaper settings:
   * Address: http://localhost:8099/newspapers
   * Param: N/A
   * Response: A list of supported Newspaper setting entries.
   
* For Newspaper settings by Country Name:
   * Address: http://localhost:8099/newspapers/country-name/{countryName}
   * Param: \"countryName\"
   * Response: A list of supported Newspapers for given \"countryName\"
   
* For Newspaper settings by Language Id:
   * Address: http://localhost:8099/newspapers/language-id/{languageId}
   * Param: \"languageId\"
   * Response: A list of supported Newspapers for given \"languageId\"
   
### For \"Page data\"

* For all supported Page settings:
   * Address: http://localhost:8099/pages
   * Param: N/A
   * Response: A list of all supported Page setting entries.
   
* For Top level pages settings by NewsPaper Id:
   * Address: http://localhost:8099/pages/newspaper-id/{newsPaperId}/top-level-pages
   * Param: \"newsPaperId\"
   * Response: A list of Top level pages for given \"newsPaperId\"
   
* For Child pages settings by Top level page Id:
   * Address: http://localhost:8099/pages//top-level-page-id/{pageId}
   * Param: \"pageId\"
   * Response: A list of Child pages for given \"pageId\"
   
### For \"Article data\"
   
* For latest Article data by page Id:
   * Address: http://localhost:8099/articles/page-id/{pageId}?article_count={resultSize}
   * Param: \"pageId\", \"resultSize\" => default 5 if no value/<=0 provided, max = 10.
   * Response: A list of article data for given \"pageId\"
   
* For Article data pagination by page Id:
   * Address: http://localhost:8099/articles/page-id/{pageId}/last-article-id/{lastArticleId}?article_count={resultSize}
   * Param: \"pageId\",\"lastArticleId\", \"resultSize\" => Same as above
   * Response: A list of article data for given \"pageId\" after \"lastArticleId\".
   
* For most recent Article data by Top level page Id:
   * Address: http://localhost:8099/articles/top-level-page-id/{topLevelPageId}/latest-article
   * Param: \"topLevelPageId\"
   * Response: Most recent article data for given \"topLevelPageId\"
   
#### In case of invalid param/end of data \"404 Not Found\" status will be returned.

 
   
   
