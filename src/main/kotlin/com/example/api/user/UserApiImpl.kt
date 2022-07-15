package com.example.api.user

import com.example.api.utils.ApiResponse
import com.example.api.utils.security.decrypt
import com.example.api.utils.security.verify
import com.example.database.dto.UserDTO
import com.example.database.users.UserDao
import io.ktor.http.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserApiImpl(
    val userDao: UserDao
):UserApi {


    override suspend fun createUser(userDTO: UserDTO): Int? = newSuspendedTransaction {
        val user = userDao.getUserByLogin(userDTO.login)
        if (user != null)
            return@newSuspendedTransaction null
        val decryptPassword = userDTO.password.decrypt()
        return@newSuspendedTransaction userDao.insert(userDTO = userDTO.copy(password = decryptPassword))
    }

    override suspend fun deleteUser(id: Int) = newSuspendedTransaction {
        userDao.findById(id) ?: return@newSuspendedTransaction null
        return@newSuspendedTransaction userDao.delete(id = id)
    }

    override suspend fun updateUser(id: Int, userDTO: UserDTO): UserDTO? = newSuspendedTransaction {
        userDao.findById(id) ?: return@newSuspendedTransaction null
        return@newSuspendedTransaction userDao.update(id = id, userDTO = userDTO).copy(id = id)
    }

    override suspend fun authUser(login: String, password: String): ApiResponse<Int> = newSuspendedTransaction {
        val user = userDao.getUserByLogin(login = login)
            ?:
                return@newSuspendedTransaction ApiResponse.Error("User with that login doesn't exist", HttpStatusCode.BadRequest)
        return@newSuspendedTransaction if (user.password.verify(password))
            ApiResponse.Success(user.id!!)
        else
            ApiResponse.Error("Check your password and repeat", HttpStatusCode.BadRequest)
    }

    override suspend fun findById(id: Int): UserDTO? = newSuspendedTransaction {
        return@newSuspendedTransaction userDao.findById(id = id)
    }

    override suspend fun getAllUsers(): List<UserDTO> = newSuspendedTransaction {
        return@newSuspendedTransaction userDao.getAllUsers()
    }


}

