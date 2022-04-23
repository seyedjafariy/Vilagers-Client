package com.kualagames.shared.components.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.kualagames.shared.components.DIComponent
import com.kualagames.shared.utils.asValue
import org.koin.core.component.get
import org.koin.core.scope.Scope

interface LoginComponent {

    val states: Value<State>

    fun onRegisterClicked()
    fun onLoginClicked()

    data class State(
        val loading : Boolean = true,
        val echoData : String = "",
    )
}

class LoginComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
    private val openRegister : () -> Unit
) : DIComponent(componentContext, parentScope, listOf(loginModule)), LoginComponent {

    private val store : LoginStore = instanceKeeper.getStore {
        get()
    }

    override val states: Value<LoginComponent.State> = store.asValue()
    override fun onRegisterClicked() {
        openRegister()
    }

    override fun onLoginClicked() {
        store.accept(LoginStore.Intent.Login)
    }
}