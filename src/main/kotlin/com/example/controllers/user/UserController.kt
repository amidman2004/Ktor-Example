package com.example.controllers.user

import com.example.controllers.model.UserCredentials
import com.example.database.dto.UserDTO
import com.example.utils.ResponseResult

interface UserController {

    suspend fun registerUser(userDTO: UserDTO):ResponseResult<Int>

    suspend fun deleteUser(id: Int?):ResponseResult<String>

    suspend fun getUserById(id: Int?):ResponseResult<UserDTO>

    suspend fun getAllUsers():ResponseResult<List<UserDTO>>

    suspend fun updateUser(id: Int?,userDTO: UserDTO):ResponseResult<UserDTO>

    suspend fun authenticate(userCredentials: UserCredentials):ResponseResult<String>

}