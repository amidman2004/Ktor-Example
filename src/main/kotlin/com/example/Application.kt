package com.example

import com.example.database.DataBase
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import com.example.plugins.*
import com.example.routing.configureRouting

fun main() {

    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        installPlugins()
        DataBase().init()
        configureRouting()
    }.start(wait = true)
}
