package com.kualagames.compose.ui.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.childAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.fade
import com.kualagames.compose.ui.auth.AuthScreen
import com.kualagames.compose.ui.main.MainScreen
import com.kualagames.compose.ui.splash.SplashScreen
import com.kualagames.shared.components.root.RootComponent

@Composable
fun RootScreen(component: RootComponent) {
    Children(
        component.routerState,
        animation = childAnimation(fade())
    ) {
        val unit = when (val child = it.instance) {
            is RootComponent.Child.Main -> MainScreen(child.component)
            is RootComponent.Child.Auth -> AuthScreen(child.component)
            is RootComponent.Child.Splash -> SplashScreen(child.component)
        }
    }
}
