package com.example.api.utils.security

import at.favre.lib.crypto.bcrypt.BCrypt


fun String.decrypt():String = BCrypt.withDefaults().hashToString(4,this.toCharArray())

fun String.verify(password:String):Boolean = BCrypt.verifyer().verify(password.toCharArray(),this).verified

