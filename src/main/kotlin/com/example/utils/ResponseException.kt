package com.example.utils

import io.ktor.http.*

data class ResponseException(
    val errorMessage:String?,
    val statusCode: HttpStatusCode?
):Exception()

fun <T> Exception.handleResponseException():ResponseResult<T>{
    return if (this is ResponseException){
        ResponseResult.Error(message = errorMessage ?: "NotFound",status = statusCode ?: HttpStatusCode.NotFound)
    }else{
        ResponseResult.Error(message = "Not Found", status = HttpStatusCode.NotFound)
    }
}

