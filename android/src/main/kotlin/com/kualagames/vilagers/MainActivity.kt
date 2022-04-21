package com.kualagames.vilagers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.kualagames.compose.ui.root.RootScreen
import com.kualagames.shared.components.RootComponent
import com.kualagames.shared.components.RootComponentImpl
import com.kualagames.shared.database.DatabaseFactory
import com.kualagames.shared.settings.SettingsStorage
import com.kualagames.vilagers.database.MainDB
import com.kualagames.vilagers.ui.theme.VilagersTheme
import com.squareup.sqldelight.android.AndroidSqliteDriver

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = DatabaseFactory.create(AndroidSqliteDriver(
            schema = MainDB.Schema,
            context = this,
            name = "VilagersDatabase.db"
        ))

        val settingsStorage = SettingsStorage(database.settingsQueries)
        val component = rootComponent(defaultComponentContext(), settingsStorage)

        setContent {
            VilagersTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    RootScreen(component)
                }
            }
        }
    }

    private fun rootComponent(componentContext: ComponentContext, settingsStorage: SettingsStorage): RootComponent =
        RootComponentImpl(
            componentContext = componentContext,
            storeFactory = LoggingStoreFactory(TimeTravelStoreFactory()),
            settingsStorage,
        )
}

