package com.kualagames.shared.components.login

import com.arkivanov.mvikotlin.core.store.Store
import com.kualagames.shared.components.login.LoginComponent.State
import com.kualagames.shared.components.login.LoginStore.Intent

interface LoginStore : Store<Intent, State, Nothing>{

    sealed interface Intent {
        object Login : Intent
    }
}