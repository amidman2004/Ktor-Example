package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

data class AuthConfig(
    val secret:String,
    val issuer:String,
    val audience:String,
    val myRealm:String
)

fun Application.configureAuthentication(){
    val config:AuthConfig by inject()

    install(Authentication){

        jwt {
            realm = config.myRealm
            val verifier = JWT
                .require(Algorithm.HMAC256(config.secret))
                .withAudience(config.audience)
                .withIssuer(config.issuer)
                .build()
            verifier(verifier)
            validate {jwtCredential ->
                if (jwtCredential.payload.getClaim("id") != null)
                    JWTPrincipal(jwtCredential.payload)
                else
                    null
            }
            challenge {defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}