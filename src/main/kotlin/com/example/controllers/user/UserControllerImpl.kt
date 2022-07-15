package com.example.controllers.user

import com.example.api.user.UserApi
import com.example.auth.TokenProvider
import com.example.controllers.model.UserCredentials
import com.example.database.dto.UserDTO
import com.example.utils.*
import io.ktor.http.*

class UserControllerImpl(
    val userApi: UserApi,
    val tokenProvider: TokenProvider
): UserController {

    override suspend fun registerUser(userDTO: UserDTO): ResponseResult<Int> {
        return try {
            val id = userApi.createUser(userDTO = userDTO)
                .handleNullable(
                    message = "User with that login already exist",
                    statusCode = HttpStatusCode.BadRequest
                )
            ResponseResult.Success(
                data = id,
                status = HttpStatusCode.Created
            )
        }catch (e:Exception){
            e.handleResponseException()
        }
    }

    override suspend fun deleteUser(id: Int?): ResponseResult<String> {
        return try {
            val userId = id.handleNullableId()
            userApi.deleteUser(id = userId).handleNullable(message = "User Not Found")
            ResponseResult.Success(
                data = "No Content",
                status = HttpStatusCode.NoContent
            )
        }catch (e:Exception){
            e.handleResponseException()
        }
    }

    override suspend fun getUserById(id: Int?): ResponseResult<UserDTO> {
        return try {
            val userId = id.handleNullableId()
            val user = userApi.findById(userId).handleNullable("User Not Found")
            ResponseResult.Success(data = user)
        }catch (e:Exception){
            e.handleResponseException()
        }
    }

    override suspend fun getAllUsers(): ResponseResult<List<UserDTO>> {
        return try {
            val users = userApi.getAllUsers()
            ResponseResult.Success(users)
        }catch (e:Exception){
            e.handleResponseException()
        }
    }

    override suspend fun updateUser(id: Int?, userDTO: UserDTO): ResponseResult<UserDTO> {
        return try {
            val userId = id.handleNullableId()
            val user = userApi.updateUser(userId,userDTO).handleNullable(
                "User Not Found"
            )
            return ResponseResult.Success(user)
        }catch (e:Exception){
            e.handleResponseException()
        }
    }

    override suspend fun authenticate(userCredentials: UserCredentials): ResponseResult<String> {
        return try {
            val userId = userApi.authUser(userCredentials.login,userCredentials.password).handleApiResponse()
            val token  = tokenProvider.createToken(userId)
            ResponseResult.Success(
                token,
            )
        }catch (e: Exception){
            e.handleResponseException()
        }
    }


}