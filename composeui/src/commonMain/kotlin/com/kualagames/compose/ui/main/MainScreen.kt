package com.kualagames.compose.ui.main

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.kualagames.compose.ui.rooms.RoomsScreen
import com.kualagames.shared.components.main.MainComponent

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun MainScreen(component: MainComponent) = Children(component.routerState) {
    when (val child = it.instance) {
        is MainComponent.Child.Rooms -> RoomsScreen(child.component)
    }
}
