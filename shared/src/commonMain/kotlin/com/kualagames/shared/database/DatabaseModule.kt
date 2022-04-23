package com.kualagames.shared.database

import com.kualagames.shared.settings.SettingsStorage
import com.kualagames.vilagers.database.MainDB
import org.koin.dsl.module

internal val databaseModule = module {
    single { DatabaseFactory.create(get()) }

    factory { get<MainDB>().settingsQueries }

    factory {
        SettingsStorage(get())
    }
}