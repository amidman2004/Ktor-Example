package com.example.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.auth.refresh_token.RefreshTokenDTO
import com.example.auth.refresh_token.api.RefreshTokenApi
import com.example.auth.refresh_token.model.TokensPair
import com.example.plugins.AuthConfig
import com.example.plugins.ResponseException
import io.ktor.http.*
import java.util.*

class TokenProviderImpl(
    val authConfig: AuthConfig,
    val tokenApi: RefreshTokenApi
):TokenProvider {

    private val accessTokenExpires = 3600000L * 24
    private val refreshTokenExpires = 3600000L * 24 * 28
    override suspend fun createToken(userId: Int, role: ROLES): String {
        return JWT.create()
            .withAudience(authConfig.audience)
            .withIssuer(authConfig.issuer)
            .withClaim("id", userId)
            .withClaim("role", role.name)
            .withExpiresAt(Date(System.currentTimeMillis() + accessTokenExpires))
            .sign(Algorithm.HMAC256(authConfig.secret))
    }

    override suspend fun createRefreshToken(userId: Int, role: ROLES): String {
        val token = UUID.randomUUID().toString()
        val expiresAt = System.currentTimeMillis() + refreshTokenExpires
        val tokenDTO = RefreshTokenDTO(
            userId = userId,
            refreshToken = token,
            expiresAt = expiresAt,
            role = role.name
        )
        return tokenApi.refreshToken(tokenDTO).refreshToken
    }

    override suspend fun refreshTokens(refreshToken: String): TokensPair {
        val tokenDTO = tokenApi.getTokenDTO(refreshToken)
        val currentTime = System.currentTimeMillis()
        if (tokenDTO.expiresAt < currentTime)
            throw ResponseException(
                "Refresh Token has Expired",
                HttpStatusCode.BadRequest
            )
        val userId = tokenDTO.userId
        val role = ROLES.valueOf(tokenDTO.role)
        return createTokens(userId,role)
    }

    override suspend fun createTokens(userId: Int, role: ROLES): TokensPair {
        val rToken = createRefreshToken(userId,role)
        val aToken = createToken(userId,role)
        return TokensPair(refreshToken = rToken, accessToken = aToken)
    }


}

