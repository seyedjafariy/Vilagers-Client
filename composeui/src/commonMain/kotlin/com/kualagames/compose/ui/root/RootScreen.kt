package com.kualagames.compose.ui.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.childAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.fade
import com.kualagames.compose.ui.main.MainScreen
import com.kualagames.shared.component.RootComponent

@Composable
fun RootScreen(component: RootComponent) {
    Children(
        component.routerState,
        animation = childAnimation(fade())
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Main -> MainScreen(child.component)
        }
    }
}
