package com.example.auth.refresh_token

data class RefreshTokenDTO(
    val userId:Int,
    val refreshToken:String,
    val expiresAt:Long,
    val role:String
)
