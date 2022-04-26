package com.kualagames.shared.components.rooms

import org.koin.dsl.module

val roomsModule = module {
    factory { RoomsAPI(get()) }

    factory { RoomsStoreProvider(get(), get()).provide() }
}