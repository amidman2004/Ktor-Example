package com.example.database.users

import com.example.database.dto.UserDTO
import org.jetbrains.exposed.sql.*
import com.example.database.users.utils.mapToUserDTO

object Users: Table(),UserDao {

    val id = integer("id").autoIncrement()

    val surname = varchar("surname",16) // фамилия
    val firstName = varchar("firstName",16) // имя
    val fatherName = varchar("fatherName",16).nullable() // отчество

    val login = varchar("login",16).uniqueIndex() // логин
    val password = varchar("password",64) // пароль

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    override fun insert(userDTO: UserDTO):Int{
        return insert {
            it[surname] = userDTO.surname
            it[firstName] = userDTO.firstName
            it[fatherName] = userDTO.fatherName
            it[login] = userDTO.login
            it[password] = userDTO.password
        }[id]
    }

    override fun delete(id:Int){
        deleteWhere{ Users.id.eq(id) }
    }

    override fun update(id: Int, userDTO: UserDTO): UserDTO {
        update({ Users.id.eq(id) }) {
            it[login] = userDTO.login
            it[password] = userDTO.password
            it[firstName] = userDTO.firstName
            it[fatherName] = userDTO.fatherName
            it[surname] = userDTO.surname
        }
        return userDTO
    }

    override fun findById(id:Int): UserDTO? {
        return select {
            Users.id eq id
        }.mapNotNull {
            it.mapToUserDTO()
        }.singleOrNull()
    }

    override fun getAllUsers():List<UserDTO>{
        return selectAll().mapNotNull {
            it.mapToUserDTO()
        }
    }

    override fun getUserByLogin(login: String): UserDTO? {
        return select {
                Users.login eq login
            }
            .singleOrNull()?.mapToUserDTO()
    }


}


