package com.example.di

import com.example.auth.TokenProvider
import com.example.auth.TokenProviderImpl
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
        TokenProviderImpl(authConfig = get())
    }

}