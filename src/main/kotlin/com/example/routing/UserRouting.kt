package com.example.routing

import com.example.controllers.model.UserCredentials
import com.example.controllers.user.UserController
import com.example.database.dto.UserDTO
import com.example.utils.sendResponseResult
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.configureUserRouting(){

    val userController = inject<UserController>().value
    route("/users"){
        post("/register") {
            val userDTO = call.receive<UserDTO>()
            val response = userController.registerUser(userDTO = userDTO)
            call.sendResponseResult(response = response)
        }
        authenticate{
            get("/information") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal!!.payload.getClaim("id").asInt()
                val response = userController.getUserById(id)
                call.sendResponseResult(response = response)
            }
        }
        post("/login") {
            val userCredentials = call.receive<UserCredentials>()
            val response = userController.authenticate(userCredentials)
            call.sendResponseResult(response)
        }
        get("/all") {
            val response = userController.getAllUsers()
            call.sendResponseResult(response = response)
        }
        put("/{id?}") {
            val id = call.parameters["id"]?.toInt()
            val userDTO = call.receive<UserDTO>()
            val response = userController.updateUser(id, userDTO)
            call.sendResponseResult(response = response)
        }
        delete("/{id?}") {
            val id = call.parameters["id"]?.toInt()
            val response = userController.deleteUser(id = id)
            call.sendResponseResult(response = response)
        }
    }
}

