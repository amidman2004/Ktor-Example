package com.example.api.user

import com.example.api.utils.ApiResponse
import com.example.database.dto.UserDTO

interface UserApi {

    suspend fun createUser(userDTO: UserDTO):Int

    suspend fun deleteUser(id:Int)

    suspend fun findById(id:Int): UserDTO

    suspend fun getAllUsers():List<UserDTO>

    suspend fun updateUser(id: Int, userDTO: UserDTO): UserDTO

    suspend fun authUser(login:String,password:String): Int
}

