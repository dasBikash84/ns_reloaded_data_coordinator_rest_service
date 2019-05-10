package com.dasbikash.news_server_data_coordinator_rest.repositories

import com.dasbikash.news_server_data_coordinator_rest.model.database.AuthToken
import org.springframework.data.jpa.repository.JpaRepository

interface AuthTokenRepository : JpaRepository<AuthToken, String>