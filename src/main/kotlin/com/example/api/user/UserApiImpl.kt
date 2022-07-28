package com.example.api.user

import com.example.api.utils.ApiResponse
import com.example.api.utils.security.decrypt
import com.example.api.utils.security.verify
import com.example.database.dto.UserDTO
import com.example.database.users.UserDao
import com.example.plugins.ResponseException
import io.ktor.http.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserApiImpl(
    val userDao: UserDao
):UserApi {


    override suspend fun createUser(userDTO: UserDTO): Int = newSuspendedTransaction {
        val user = userDao.getUserByLogin(userDTO.login)
        if (user != null)
            throw ResponseException(
                "User with the same login already exist",
                HttpStatusCode.BadRequest
            )
        val decryptPassword = userDTO.password.decrypt()
        return@newSuspendedTransaction userDao.insert(userDTO = userDTO.copy(password = decryptPassword))
    }

    override suspend fun deleteUser(id: Int): Unit = newSuspendedTransaction {
        userDao.findById(id) ?: throw ResponseException(
            "User Not Found",
            HttpStatusCode.NotFound
        )
        return@newSuspendedTransaction userDao.delete(id = id)
    }

    override suspend fun updateUser(id: Int, userDTO: UserDTO): UserDTO = newSuspendedTransaction {
        userDao.findById(id) ?: throw ResponseException(
            "User Not Found",
            HttpStatusCode.NotFound
        )
        return@newSuspendedTransaction userDao.update(id = id, userDTO = userDTO).copy(id = id)
    }

    override suspend fun authUser(login: String, password: String): Int = newSuspendedTransaction {
        val user = userDao.getUserByLogin(login = login) ?: throw ResponseException(
            "User with that login doesn't exist",
            HttpStatusCode.BadRequest
        )
        if (user.password.verify(password))
            user.id ?: throw ResponseException(
                "Not Found",
                HttpStatusCode.NotFound
            )
        else
            throw ResponseException("Check your password and repeat", HttpStatusCode.BadRequest)
    }

    override suspend fun findById(id: Int): UserDTO = newSuspendedTransaction {
        return@newSuspendedTransaction userDao.findById(id = id) ?: throw ResponseException(
            "User Not Found",
            HttpStatusCode.NotFound
        )
    }

    override suspend fun getAllUsers(): List<UserDTO> = newSuspendedTransaction {
        return@newSuspendedTransaction userDao.getAllUsers()
    }


}

