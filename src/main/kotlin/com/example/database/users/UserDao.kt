package com.example.database.users

import com.example.database.dto.UserDTO

interface UserDao {

    fun insert(userDTO: UserDTO):Int

    fun delete(id:Int)

    fun update(id: Int,userDTO: UserDTO): UserDTO

    fun findById(id:Int): UserDTO?

    fun getAllUsers():List<UserDTO>

    fun getUserByLogin(login:String):UserDTO?
}