package com.example.api.utils

import io.ktor.http.*

sealed class ApiResponse<T>(val data:T? = null,val error:String? = null,val exceptionStatusCode: HttpStatusCode? = null){
    class Success<T>(data: T):ApiResponse<T>(data = data)
    class Error<T>(error: String = "Not Found",exceptionStatusCode: HttpStatusCode = HttpStatusCode.NotFound):
        ApiResponse<T>(error = error,exceptionStatusCode = exceptionStatusCode)
}

