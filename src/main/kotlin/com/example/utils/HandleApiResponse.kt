package com.example.utils

import com.example.api.utils.ApiResponse

fun <T> ApiResponse<T>.handleApiResponse():T{
    return when(this){
        is ApiResponse.Success -> this.data!!
        is ApiResponse.Error -> throw ResponseException(errorMessage = this.error, statusCode = this.exceptionStatusCode)
    }
}
