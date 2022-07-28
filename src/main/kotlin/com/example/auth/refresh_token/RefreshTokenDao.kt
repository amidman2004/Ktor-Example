package com.example.auth.refresh_token

interface RefreshTokenDao {
    fun insert(tokenDTO:RefreshTokenDTO):RefreshTokenDTO

    fun update(userId:Int,tokenDTO: RefreshTokenDTO):RefreshTokenDTO

    fun findById(userId: Int):RefreshTokenDTO?

    fun findByToken(token:String):RefreshTokenDTO?

}