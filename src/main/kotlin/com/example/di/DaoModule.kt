package com.example.di

import com.example.controllers.user.UserControllerImpl
import com.example.database.users.UserDao
import com.example.database.users.Users
import org.koin.core.module.dsl.singleOf
import org.koin.core.scope.get
import org.koin.dsl.module

val daoModule = module {

    single<UserDao> {
        Users
    }

}