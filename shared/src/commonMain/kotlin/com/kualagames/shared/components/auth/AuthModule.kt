package com.kualagames.shared.components.auth

import org.koin.dsl.module

val authModule = module {
    factory {
        AuthAPI(get())
    }
}