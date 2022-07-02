package com.example.utils

import io.ktor.http.*

sealed class ResponseResult<T>(
    val data:T? = null,
    val status:HttpStatusCode,
    val message:String? = null
){
    class Error<T>(message: String, status: HttpStatusCode):ResponseResult<T>(message = message,status = status)

    class Success<T>(data: T,status: HttpStatusCode = HttpStatusCode.OK):ResponseResult<T>(data = data,status = status)
}

//fun <T> ResponseResult<T>.handle():T{
//    when(this){
//        is ResponseResult.Error -> throw ResponseException(
//                errorMessage = message,
//                statusCode = status
//            )
//        is ResponseResult.Success -> return data ?: throw ResponseException(
//            errorMessage = "No Content",
//            statusCode = HttpStatusCode.NoContent
//        )
//    }
//}