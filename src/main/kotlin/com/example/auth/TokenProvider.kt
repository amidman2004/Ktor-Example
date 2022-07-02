package com.example.auth

interface TokenProvider {
    fun createToken(userId:Int):String
}