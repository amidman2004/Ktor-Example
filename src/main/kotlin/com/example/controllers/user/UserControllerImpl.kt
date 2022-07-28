package com.example.controllers.user

import com.example.api.user.UserApi
import com.example.auth.TokenProvider
import com.example.auth.refresh_token.model.TokenData
import com.example.auth.refresh_token.model.TokensPair
import com.example.controllers.model.UserCredentials
import com.example.database.dto.UserDTO
import com.example.plugins.ResponseException
import com.example.utils.*
import io.ktor.http.*

class UserControllerImpl(
    val userApi: UserApi,
    val tokenProvider: TokenProvider
): UserController {

    override suspend fun registerUser(userDTO: UserDTO): Int {
        return userApi.createUser(userDTO = userDTO)
    }

    override suspend fun deleteUser(id: Int?): String {
        val userId = id ?: throw ResponseException(
            "Missing Id Parameter",
            HttpStatusCode.BadRequest
        )
        userApi.deleteUser(id = userId)
        return "No Content"
    }

    override suspend fun getUserById(id: Int?): UserDTO {
        val userId = id ?: throw ResponseException(
            "Missing Id Parameter",
            HttpStatusCode.BadRequest
        )
        return userApi.findById(userId)
    }

    override suspend fun getAllUsers(): List<UserDTO> {
        return userApi.getAllUsers()
    }

    override suspend fun updateUser(id: Int?, userDTO: UserDTO): UserDTO {
        val userId = id ?: throw ResponseException(
            "Missing Id Parameter",
            HttpStatusCode.BadRequest
        )
        return userApi.updateUser(userId,userDTO)
    }

    override suspend fun authenticate(userCredentials: UserCredentials): TokensPair {
        val userId = userApi.authUser(userCredentials.login,userCredentials.password)
        return tokenProvider.createTokens(userId)
    }

    override suspend fun refreshTokens(tokenData: TokenData): TokensPair {
        return tokenProvider.refreshTokens(tokenData.refreshToken)
    }


}