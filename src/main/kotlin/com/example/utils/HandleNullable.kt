package com.example.utils

import io.ktor.http.*

fun <T> T?.handleNullable(message:String? = null,statusCode: HttpStatusCode? = null):T{
    return this ?: throw ResponseException(errorMessage = message,statusCode = statusCode)
}