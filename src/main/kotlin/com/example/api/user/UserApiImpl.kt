package com.example.api.user

import com.example.database.dto.UserDTO
import com.example.database.users.UserDao
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserApiImpl(
    val userDao: UserDao
):UserApi {


    override suspend fun createUser(userDTO: UserDTO): Int? = newSuspendedTransaction {
        val user = userDao.getUserByLogin(userDTO.login)
        if (user != null)
            return@newSuspendedTransaction null
        return@newSuspendedTransaction userDao.insert(userDTO = userDTO)
    }

    override suspend fun deleteUser(id: Int) = newSuspendedTransaction {
        userDao.findById(id) ?: return@newSuspendedTransaction null
        return@newSuspendedTransaction userDao.delete(id = id)
    }

    override suspend fun updateUser(id: Int, userDTO: UserDTO): UserDTO? = newSuspendedTransaction {
        userDao.findById(id) ?: return@newSuspendedTransaction null
        return@newSuspendedTransaction userDao.update(id = id, userDTO = userDTO).copy(id = id)
    }

    override suspend fun authUser(login: String, password: String): Int? = newSuspendedTransaction {
        val user = userDao.getUserByLogin(login = login) ?: return@newSuspendedTransaction null
        return@newSuspendedTransaction if (user.password == password)
            user.id
        else
            null
    }

    override suspend fun findById(id: Int): UserDTO? = newSuspendedTransaction {
        return@newSuspendedTransaction userDao.findById(id = id)
    }

    override suspend fun getAllUsers(): List<UserDTO> = newSuspendedTransaction {
        return@newSuspendedTransaction userDao.getAllUsers()
    }


}