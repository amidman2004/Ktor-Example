package com.example.plugins

import com.example.di.apiModule
import com.example.di.authModule
import com.example.di.controllerModule
import com.example.di.daoModule
import io.ktor.server.application.*
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureKtorDI(){
    install(Koin){
        modules(
            apiModule,
        )
        modules(
            controllerModule,
        )
        modules(
            daoModule,
        )
        modules(
            authModule
        )
    }
}