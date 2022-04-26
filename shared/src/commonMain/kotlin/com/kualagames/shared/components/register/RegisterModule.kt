package com.kualagames.shared.components.register

import org.koin.dsl.module

val registerModule = module {
    factory { RegisterStoreProvider(get(), get(), get()).provide() }
}