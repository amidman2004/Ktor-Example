package com.example.auth.refresh_token.api

import com.example.auth.refresh_token.RefreshTokenDTO

interface RefreshTokenApi {

    suspend fun refreshToken(refreshTokenDTO: RefreshTokenDTO):RefreshTokenDTO

    suspend fun getTokenDTO(refreshToken:String):RefreshTokenDTO
}