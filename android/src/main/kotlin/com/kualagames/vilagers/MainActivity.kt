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
import com.kualagames.shared.component.RootComponent
import com.kualagames.shared.component.RootComponentImpl
import com.kualagames.vilagers.ui.theme.VilagersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = rootComponent(defaultComponentContext())

        setContent {
            VilagersTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    RootScreen(component)
                }
            }
        }
    }

    private fun rootComponent(componentContext: ComponentContext): RootComponent =
        RootComponentImpl(
            componentContext = componentContext,
            storeFactory = LoggingStoreFactory(TimeTravelStoreFactory()),
        )
}

