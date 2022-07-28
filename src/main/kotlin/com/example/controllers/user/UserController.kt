package com.example.controllers.user

import com.example.controllers.model.UserCredentials
import com.example.database.dto.UserDTO
import com.example.auth.refresh_token.model.TokenData
import com.example.auth.refresh_token.model.TokensPair

interface UserController {

    suspend fun registerUser(userDTO: UserDTO):Int

    suspend fun deleteUser(id: Int?):String

    suspend fun getUserById(id: Int?):UserDTO

    suspend fun getAllUsers():List<UserDTO>

    suspend fun updateUser(id: Int?,userDTO: UserDTO):UserDTO

    suspend fun authenticate(userCredentials: UserCredentials):TokensPair

    suspend fun refreshTokens(tokenData: TokenData):TokensPair

}