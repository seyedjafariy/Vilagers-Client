package com.kualagames.shared.components.login

import com.arkivanov.mvikotlin.core.store.Store
import com.kualagames.shared.components.login.LoginComponent.State
import com.kualagames.shared.components.login.LoginStore.Intent
import com.kualagames.shared.components.login.LoginStore.Label

interface LoginStore : Store<Intent, State, Label>{

    sealed interface Intent {
        data class Login(
            val username : String,
            val password : String,
        ) : Intent
    }

    sealed interface Label {
        object Success : Label
    }
}