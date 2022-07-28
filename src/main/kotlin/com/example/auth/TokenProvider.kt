package com.example.auth

import com.example.auth.refresh_token.model.TokensPair

interface TokenProvider {
    suspend fun createToken(userId:Int, role: ROLES = ROLES.USER):String

    suspend fun createRefreshToken(userId: Int, role: ROLES = ROLES.USER):String

    suspend fun refreshTokens(refreshToken:String): TokensPair

    suspend fun createTokens(userId: Int,role: ROLES = ROLES.USER):TokensPair
}