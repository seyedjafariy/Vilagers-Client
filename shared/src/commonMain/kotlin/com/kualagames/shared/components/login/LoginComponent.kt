package com.kualagames.shared.components.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.kualagames.shared.components.DIComponent
import com.kualagames.shared.utils.*
import org.koin.core.component.get
import org.koin.core.scope.Scope

interface LoginComponent {

    val states: Value<State>

    fun onRegisterClicked()
    fun onLoginClicked(username: String, password: String)
    fun forgotPasswordClicked()

    data class State(
        val loading: Boolean = false,
        val showLoginFailed : Boolean = false,
        val showWrongPass : Boolean = false,
        val showWrongUsername : Boolean = false,
    )
}

class LoginComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
    private val openRegister: () -> Unit,
    private val loginSuccessful: () -> Unit,
) : DIComponent(componentContext, parentScope, listOf(loginModule)), LoginComponent {

    private val store: LoginStore = instanceKeeper.getStore {
        get<LoginStore>()
    }.apply {
        onNextLabel {
            when (it) {
                LoginStore.Label.Success -> {
                    loginSuccessful()
                }
            }
        }.addTo(createdDisposables)
    }

    override val states: Value<LoginComponent.State> = store.asValue()
    override fun onRegisterClicked() {
        openRegister()
    }

    override fun onLoginClicked(username: String, password: String) {
        store.accept(LoginStore.Intent.Login(username, password))
    }

    override fun forgotPasswordClicked() {
        TODO("Not yet implemented")
    }
}