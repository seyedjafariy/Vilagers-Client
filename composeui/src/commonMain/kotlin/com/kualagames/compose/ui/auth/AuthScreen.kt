package com.kualagames.compose.ui.auth

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.kualagames.compose.ui.login.LoginScreen
import com.kualagames.compose.ui.register.RegisterScreen
import com.kualagames.shared.components.auth.AuthComponent

@Composable
fun AuthScreen(component: AuthComponent) {
    Children(component.routerState){
        when (val child = it.instance) {
            is AuthComponent.Child.Login -> LoginScreen(child.component)
            is AuthComponent.Child.Register -> RegisterScreen(child.component)
        }
    }
}