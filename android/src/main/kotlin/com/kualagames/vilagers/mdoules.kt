@file:OptIn(InternalAPI::class)

package com.kualagames.vilagers

import android.content.Context
import android.util.Log
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.logger.DefaultLogger
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.kualagames.vilagers.BuildConfig.DEBUG
import com.kualagames.vilagers.database.MainDB
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import io.ktor.util.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.binds
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val dbDriverModule = module {
    factory {
        AndroidSqliteDriver(
            schema = MainDB.Schema,
            context = get(),
            name = "VilagersDatabase.db"
        )
    } binds arrayOf(SqlDriver::class)
}

val storesModule = module {
    single { LoggingStoreFactory(TimeTravelStoreFactory()) } binds arrayOf(StoreFactory::class)
}

val networkEngineModule = module {
    factory<HttpClientEngine> {
        OkHttpEngine(OkHttpConfig().apply {
            preconfigured = get()
        })
    }

    factory {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
            DefaultLogger.log(message)
        }

        httpLoggingInterceptor.level = if (DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        httpLoggingInterceptor
    }

    factory {
        Cache(get<Context>().cacheDir, 50_000_000)
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)// Set connection timeout
            .readTimeout(120, TimeUnit.SECONDS)// Read timeout
            .writeTimeout(120, TimeUnit.SECONDS)// Write timeout
//            .cache(cache)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
}
