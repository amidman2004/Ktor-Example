package com.example.di

import com.example.api.user.UserApi
import com.example.api.user.UserApiImpl
import com.example.database.users.UserDao
import org.koin.dsl.module


val apiModule = module {
    single<UserApi> {
        UserApiImpl(
            userDao = get<UserDao>()
        )
    }
}