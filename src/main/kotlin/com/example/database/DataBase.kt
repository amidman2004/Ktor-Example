package com.example.database

import com.example.database.users.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.Schema
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource


class DataBase {
    private val connectionType = "mysql"
    private val databaseName = "test_ktor"
    private val host = "127.0.0.1"
    private val port = "3306"

    private val userName = "root"
    private val password = "admin"

    fun init(){

//        migrate()

        val database = Database.connect(
            url = "jdbc:$connectionType://$host:$port/$databaseName",
            user = userName,
            password = password
        )

        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(Users)
        }

    }
    private fun migrate(){
        val hikariConfig = HikariConfig("db.properties")
        val dataSource = HikariDataSource(hikariConfig)

        val flyway = Flyway.configure().validateOnMigrate(true).dataSource(dataSource).load()
        flyway.repair()
        flyway.migrate()

    }
}

