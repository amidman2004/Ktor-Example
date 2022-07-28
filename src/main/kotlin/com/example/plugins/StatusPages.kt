package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages(){

    install(StatusPages){


        exception<ResponseException>{call, rExc ->
            call.respondText(text = rExc.errorMessage, status = rExc.statusCode)
        }
    }
}


data class ResponseException(
    val errorMessage:String,
    val statusCode: HttpStatusCode?
):Exception()

