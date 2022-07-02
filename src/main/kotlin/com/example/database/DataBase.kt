package com.example.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.example.database.users.Users

class DataBase {
    private val connectionType = "mysql"
    private val databaseName = "test_ktor"
    private val host = "localhost"
    private val port = "3306"

    private val userName = "root"
    private val password = "admin"

    fun init(){


        val database = Database.connect(
            url = "jdbc:$connectionType://$host:$port/$databaseName",
            user = userName,
            password = password
        )

        transaction(database) {
            SchemaUtils.create(Users)
        }
    }
}