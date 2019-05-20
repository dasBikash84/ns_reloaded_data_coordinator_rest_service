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

package com.dasbikash.news_server_data_coordinator_rest.model.database

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlTransient

@Entity
@Table(name = DatabaseTableNames.NEWSPAPER_TABLE_NAME)
@XmlRootElement
data class Newspaper(
        @Id var id: String = "",
        var name: String? = null,

        @ManyToOne(targetEntity = Country::class, fetch = FetchType.EAGER)
        @JoinColumn(name = "countryName")
        private var country: Country? = null,

        @ManyToOne(targetEntity = Language::class, fetch = FetchType.EAGER)
        @JoinColumn(name = "languageId")
        private var language: Language? = null,

        private var active: Boolean = true,

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "newspaper", targetEntity = Page::class)
        private var pageList: List<Page>? = null

) : DataCoordinatorRestEntity {

    @XmlElement
    fun getCountryName():String? {
        return country?.name
    }

    @XmlElement
    fun getLanguageId(): String? {
        return language?.id
    }

    @JsonIgnore
    @XmlTransient
    fun getActive(): Boolean {
        return active
    }

    fun setActive(active: Boolean) {
        this.active = active
    }

    @JsonIgnore
    @XmlTransient
    fun getCountry(): Country? {
        return this.country
    }

    fun setCountry(country: Country?) {
        this.country = country
    }

    @JsonIgnore
    @XmlTransient
    fun getLanguage(): Language? {
        return this.language
    }

    fun setLanguage(language: Language?) {
        this.language = language
    }

    @JsonIgnore
    @XmlTransient
    fun getPageList(): List<Page>? {
        return this.pageList
    }

    fun setPageList(pageList: List<Page>?) {
        this.pageList = pageList
    }

    override fun toString(): String {
        return "Newspaper(id='$id', name=$name, country=$country, language=$language, active=$active)"
    }
}