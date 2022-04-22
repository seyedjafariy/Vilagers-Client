package com.kualagames.vilagers

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.kualagames.shared.database.DatabaseFactory
import com.kualagames.shared.settings.SettingsStorage
import com.kualagames.vilagers.database.MainDB
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.dsl.binds
import org.koin.dsl.module

val databaseModule = module {
    factory {
        AndroidSqliteDriver(
            schema = MainDB.Schema,
            context = get(),
            name = "VilagersDatabase.db"
        )
    } binds arrayOf(SqlDriver::class)

    single { DatabaseFactory.create(get()) }

    factory { get<MainDB>().settingsQueries }

    factory {
        SettingsStorage(get())
    }
}

val storesModule = module {
    single { LoggingStoreFactory(TimeTravelStoreFactory()) } binds arrayOf(StoreFactory::class)
}