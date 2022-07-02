package com.example.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun ApplicationCall.sendResponseResult(response: ResponseResult<*>){
    when(response){
        is ResponseResult.Error -> {
            this.respond(message = response.message ?: "Not Found", status = response.status)
        }
        is ResponseResult.Success -> {
            response.data?.let { data->
                this.respond(message = data, status = response.status)
            }
                ?:
            this.respond(message = "No Content", status = HttpStatusCode.NoContent)
        }
    }
}


