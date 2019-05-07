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

package com.dasbikash.news_server_spring_mvc_rest_datasource.model.database.log_entities

import javax.persistence.*

@Entity
@Table(name = "exception_log")
class ErrorLog() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    var exceptionClassFullName: String? = null
    var exceptionClassSimpleName: String? = null
    @Column(columnDefinition = "text")
    var exceptionCause: String? = null
    @Column(columnDefinition = "text")
    var exceptionMessage: String? = null
    @Column(columnDefinition = "text")
    var stackTrace: String? = null
}