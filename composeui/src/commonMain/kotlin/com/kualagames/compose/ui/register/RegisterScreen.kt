@file:OptIn(ExperimentalComposeUiApi::class)

package com.kualagames.compose.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.kualagames.shared.components.register.RegisterComponent

@Composable
fun RegisterScreen(component: RegisterComponent) = Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp), verticalArrangement = Arrangement.Center) {
        val state by component.state.subscribeAsState()

        InternalRegisterScreen(state, component)
    }
}

@Composable
private fun InternalRegisterScreen(
    state: RegisterComponent.State,
    component: RegisterComponent
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val emailFocus = remember { FocusRequester() }
    val passwordFocus = remember { FocusRequester() }
    val registerButtonFocus = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        singleLine = true,
        value = username,
        isError = state.showUsernameError,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onAny = {
            emailFocus.requestFocus()
        }),
        label = {
            Text("Username")
        },
        onValueChange = {
            username = it
        },
    )

    Spacer(modifier = Modifier.height(16.dp))

    TextField(
        modifier = Modifier.focusOrder(emailFocus).fillMaxWidth().padding(horizontal = 16.dp),
        singleLine = true,
        value = email,
        isError = state.showEmailError,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onAny = {
            passwordFocus.requestFocus()
        }),
        label = {
            Text("Email")
        },
        onValueChange = {
            email = it
        },
    )

    Spacer(modifier = Modifier.height(16.dp))

    TextField(
        modifier = Modifier.focusOrder(passwordFocus).fillMaxWidth().padding(horizontal = 16.dp),
        singleLine = true,
        value = password,
        isError = state.showPasswordError,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions {
            registerButtonFocus.requestFocus()
            keyboardController?.hide()
        },
        label = {
            Text("password")
        },
        onValueChange = {
            password = it
        },
    )

    if (state.showRegisterFailed) {
        Text(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), text = "Login failed")
    } else if (state.loading) {
        CircularProgressIndicator()
    } else {
        Button(
            modifier = Modifier.focusOrder(registerButtonFocus).fillMaxWidth().padding(horizontal = 16.dp),
            onClick = {
                component.onRegisterClicked(username, email, password)
            }) {

            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                text = "Register"
            )
        }
    }
}