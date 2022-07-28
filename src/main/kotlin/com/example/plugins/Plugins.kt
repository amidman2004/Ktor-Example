package com.example.plugins

import io.ktor.server.application.*

fun Application.installPlugins(){
    configureKtorDI()
    configureSerialization()
    configureAuthentication()
    configureStatusPages()
}