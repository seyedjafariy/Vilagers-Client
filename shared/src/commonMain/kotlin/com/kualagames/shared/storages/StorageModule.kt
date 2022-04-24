package com.kualagames.shared.storages

import org.koin.dsl.module

val storageModule = module {
    single { CredentialStorage() }

    factory { SettingsStorage(get()) }
}