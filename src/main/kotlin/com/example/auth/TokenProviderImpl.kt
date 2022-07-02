package com.example.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.plugins.AuthConfig
import java.util.*

class TokenProviderImpl(
    val authConfig: AuthConfig
):TokenProvider {
    override fun createToken(userId:Int):String{
        val token = JWT.create()
            .withAudience(authConfig.audience)
            .withIssuer(authConfig.issuer)
            .withClaim("id",userId)
            .withExpiresAt(Date(System.currentTimeMillis() + 3600000L))
            .sign(Algorithm.HMAC256(authConfig.secret))
        return token
    }

}