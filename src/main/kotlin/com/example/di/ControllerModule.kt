package com.example.di

import com.example.api.user.UserApi
import com.example.controllers.user.UserController
import com.example.controllers.user.UserControllerImpl
import org.koin.dsl.module

val controllerModule = module {
    single<UserController> {
        UserControllerImpl(userApi = get<UserApi>(), tokenProvider = get())
    }
}