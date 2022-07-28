package com.example.auth.refresh_token.api

import com.example.auth.refresh_token.RefreshTokenDTO
import com.example.auth.refresh_token.RefreshTokenDao
import com.example.database.users.UserDao
import com.example.plugins.ResponseException
import io.ktor.http.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class RefreshTokenApiImpl(
    private val tokenDao: RefreshTokenDao,
    private val userDao: UserDao
):RefreshTokenApi {

    override suspend fun refreshToken(refreshTokenDTO: RefreshTokenDTO): RefreshTokenDTO = newSuspendedTransaction{
        userDao.findById(refreshTokenDTO.userId) ?: throw ResponseException(
            "User Doesn't Exist",
            HttpStatusCode.NotFound
        )
        tokenDao.findById(refreshTokenDTO.userId)?.let {
            return@newSuspendedTransaction tokenDao.update(refreshTokenDTO.userId,refreshTokenDTO)
        }
        tokenDao.insert(tokenDTO = refreshTokenDTO)
    }

    override suspend fun getTokenDTO(refreshToken: String): RefreshTokenDTO = newSuspendedTransaction{
        tokenDao.findByToken(refreshToken) ?: throw ResponseException(
            "Invalid Refresh Token",
            HttpStatusCode.BadRequest
        )
    }



//    private suspend fun isTokenUnique(refreshTokenDTO: RefreshTokenDTO):Boolean = newSuspendedTransaction{
//        tokenDao.findByToken(refreshTokenDTO.refreshToken)?.let { return@newSuspendedTransaction false }
//        tokenDao.findById(refreshTokenDTO.userId)?.let { return@newSuspendedTransaction false }
//        true
//    }
}