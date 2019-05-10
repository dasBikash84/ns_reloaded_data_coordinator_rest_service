package com.dasbikash.news_server_data_coordinator_rest.services

import com.dasbikash.news_server_data_coordinator_rest.exceptions.IllegalRequestBodyException
import com.dasbikash.news_server_data_coordinator_rest.exceptions.TokenGenerationException
import com.dasbikash.news_server_data_coordinator_rest.model.database.AuthToken
import com.dasbikash.news_server_data_coordinator_rest.repositories.AuthTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthTokenService @Autowired constructor(val authTokenRepository: AuthTokenRepository){

    fun getNewAuthToken():AuthToken{
        try {
            val newToken = AuthToken()
            authTokenRepository.save(newToken)
            return newToken
        }catch (ex:Exception){
            throw TokenGenerationException(ex)
        }
    }

    fun invalidateAuthToken(tokenId: String){
        try {
            val token = authTokenRepository.findById(tokenId).get()
            if (token.expiresOn < Date()){
                throw IllegalRequestBodyException()
            }
            token.expiresOn = Date()
            authTokenRepository.save(token)
        }catch (ex:Exception){
            throw IllegalRequestBodyException(ex)
        }
    }
}