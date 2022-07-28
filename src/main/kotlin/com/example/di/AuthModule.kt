package com.example.di

import com.example.auth.TokenProvider
import com.example.auth.TokenProviderImpl
import com.example.auth.refresh_token.RefreshTokenDao
import com.example.auth.refresh_token.RefreshTokens
import com.example.auth.refresh_token.api.RefreshTokenApi
import com.example.auth.refresh_token.api.RefreshTokenApiImpl
import com.example.plugins.AuthConfig
import org.koin.dsl.module

val authModule = module {
    single {
        AuthConfig(
            secret = "sddjnadkjjnasldmalsdmlkansdlnasaaskkdakjsdnljkasndkibaklslnlvkajsdnalnsnalnfkkjwnlnljnajklcksjlnasasmasdkbaskhjbdwybdkabadknkvnlanuwbkakdbkwbdjduwabdkkasndkjbakjbwudabfa",
            issuer = "http://0.0.0.0:8080/",
            audience = "my-audience",
            myRealm = "realm"
        )
    }
    single<TokenProvider> {
        TokenProviderImpl(authConfig = get(), tokenApi = get())
    }
    single<RefreshTokenApi> {
        RefreshTokenApiImpl(tokenDao = get(), userDao = get())
    }
    single<RefreshTokenDao> {
        RefreshTokens
    }

}