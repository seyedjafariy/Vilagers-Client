package com.kualagames.shared.components.login

import org.koin.dsl.module

val loginModule = module {
    factory {
        LoginStoreProvider(get(), get(), get()).provide()
    }

}