package com.dasbikash.news_server_data_coordinator_rest.repositories

import com.dasbikash.news_server_data_coordinator_rest.model.database.PageGroup
import org.springframework.data.jpa.repository.JpaRepository

interface PageGroupRepository : JpaRepository<PageGroup, Int>