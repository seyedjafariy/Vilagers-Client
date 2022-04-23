package com.kualagames.vilagers

import android.app.Application
import com.arkivanov.mvikotlin.timetravel.server.TimeTravelServer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.scope.Scope
import com.kualagames.shared.utils.mergedModules

class App : Application() {

    lateinit var scope : Scope

    @OptIn(KoinInternalApi::class)
    override fun onCreate() {
        super.onCreate()

        TimeTravelServer().start()

        scope = startKoin{
            androidLogger()
            androidContext(this@App)
            modules(listOf(dbDriverModule, storesModule, networkEngineModule) + mergedModules)
        }.koin.scopeRegistry.rootScope
    }
}
