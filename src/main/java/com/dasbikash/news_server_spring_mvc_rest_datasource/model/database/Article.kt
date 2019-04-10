/*
 * Copyright 2019 das.bikash.dev@gmail.com. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dasbikash.news_server_spring_mvc_rest_datasource.model.database

import com.dasbikash.news_server_parser.model.ArticleImage
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList


@Entity
@Table(name = DatabaseTableNames.ARTICLE_TABLE_NAME)
data class Article(
        @Id
        var id: String?=null,

        @ManyToOne(targetEntity = Page::class, fetch = FetchType.EAGER)
        @JoinColumn(name = "pageId")
        @JsonIgnore
        var page: Page? = null,

        var title: String? = null,
        var modificationTS: Date? = null,
        var publicationTS: Date? = null,
//        @JsonIgnore
        var modified: Date? = null,

        @Column(columnDefinition = "text")
        var articleText: String? = null,

        @ElementCollection(targetClass = ArticleImage::class)
        @CollectionTable(name = "image_links", joinColumns = [JoinColumn(name = "articleId")])
        @Column(name = "imageLink", columnDefinition = "text")
        var imageLinkList: List<ArticleImage> = ArrayList(),

        @Column(columnDefinition = "text")
        var previewImageLink: String? = null
):NsSpringRestDbEntity{
        override fun toString(): String {
                return "Article(id='$id', page=${page?.name}, title=$title, modificationTS=$modificationTS, publicationTS=$publicationTS, " +
                        "articleText=${articleText ?: ""}, imageLinkList=$imageLinkList, previewImageLink=$previewImageLink)"
        }
}