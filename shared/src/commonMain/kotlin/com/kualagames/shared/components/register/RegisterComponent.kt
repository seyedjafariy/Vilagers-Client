package com.kualagames.shared.components.register

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.kualagames.shared.components.DIComponent
import com.kualagames.shared.utils.addTo
import com.kualagames.shared.utils.asValue
import com.kualagames.shared.utils.createdDisposables
import com.kualagames.shared.utils.onNextLabel
import org.koin.core.component.get
import org.koin.core.scope.Scope

interface RegisterComponent {

    val state : Value<State>

    fun onRegisterClicked(username : String, email : String, password : String)

    data class State(
        val loading : Boolean = false,
        val showRegisterFailed : Boolean = false,
        val showPasswordError : Boolean = false,
        val showUsernameError : Boolean = false,
        val showEmailError : Boolean = false,
        val emailErrorReason : ErrorReason = ErrorReason.No,
        val usernameErrorReason : ErrorReason = ErrorReason.No,
    ){

        enum class ErrorReason{
            No,
            Duplicate,
            Bad,
            ;
        }
    }
}

class RegisterComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
    private val registerSuccess : () -> Unit,
) : DIComponent(componentContext, parentScope, listOf(registerModule)), RegisterComponent {

    private val store : RegisterStore = instanceKeeper.getStore {
        get<RegisterStore>()
    }.apply {
        onNextLabel {
            when (it) {
                RegisterStore.Label.Success -> registerSuccess()
            }
        } addTo createdDisposables
    }
    override val state: Value<RegisterComponent.State> =
        store.asValue()

    override fun onRegisterClicked(username: String, email: String, password: String) {
        store.accept(RegisterStore.Intent.Register(email, username, password))
    }
}