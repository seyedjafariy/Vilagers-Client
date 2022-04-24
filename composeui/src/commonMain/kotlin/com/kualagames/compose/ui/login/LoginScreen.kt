package com.kualagames.compose.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.kualagames.shared.components.login.LoginComponent

@Composable
fun LoginScreen(loginComponent: LoginComponent) =
    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {

            val state by loginComponent.states.subscribeAsState()

            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            TextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                value = username,
                isError = state.showWrongUsername,
                onValueChange = {
                    username = it
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                value = password,
                isError = state.showWrongUsername,
                onValueChange = {
                    password = it
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (state.showLoginFailed) {
                Text(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), text = "Login failed")
            } else if (state.loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    onClick = {
                        loginComponent.onLoginClicked(username, password)
                    }) {

                    Text(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), text = "Login")
                }
            }
        }
    }
