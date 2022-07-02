package com.example.database.dto


data class UserDTO(
    val id:Int?,
    val surname:String,
    val firstName:String,
    val fatherName:String?,
    val login: String,
    val password:String
)
