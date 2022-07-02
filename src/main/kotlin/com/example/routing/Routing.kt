package com.example.routing

import com.example.routing.configureUserRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {

    routing {
        configureUserRouting()
    }

}
