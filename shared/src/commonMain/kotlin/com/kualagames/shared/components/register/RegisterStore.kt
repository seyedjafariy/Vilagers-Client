package com.kualagames.shared.components.register

import com.arkivanov.mvikotlin.core.store.Store
import com.kualagames.shared.components.register.RegisterComponent.State
import com.kualagames.shared.components.register.RegisterStore.Intent
import com.kualagames.shared.components.register.RegisterStore.Label

interface RegisterStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class Register(
            val email: String,
            val username: String,
            val password: String,
        ) : Intent
    }

    sealed interface Label {
        object Success : Label
    }
}
