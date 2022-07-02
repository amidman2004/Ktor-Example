package com.example.utils

import io.ktor.http.*

fun Int?.handleNullableId():Int{
    return this ?: throw ResponseException(
        errorMessage = "Missing Id",
        statusCode = HttpStatusCode.BadRequest
    )
}