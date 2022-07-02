package com.example.database.users.utils

import com.example.database.dto.UserDTO
import org.jetbrains.exposed.sql.ResultRow
import com.example.database.users.Users

fun ResultRow.mapToUserDTO(): UserDTO {
    return UserDTO(
        id = this[Users.id],
        surname = this[Users.surname],
        firstName = this[Users.firstName],
        fatherName = this[Users.fatherName],
        login = this[Users.login],
        password = this[Users.password]
    )
}