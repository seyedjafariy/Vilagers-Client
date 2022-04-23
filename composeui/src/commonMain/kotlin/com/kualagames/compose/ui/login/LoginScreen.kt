package com.kualagames.compose.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.kualagames.shared.components.login.LoginComponent

@Composable
fun LoginScreen(loginComponent: LoginComponent) {
    val state by loginComponent.states.subscribeAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            if (state.loading) {
                CircularProgressIndicator(Modifier.clickable {
                    loginComponent.onLoginClicked()
                })
            }
            Text(text = state.echoData)
        }
    }
}