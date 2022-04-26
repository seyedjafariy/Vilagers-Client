package com.kualagames.shared.components.register

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kualagames.shared.components.auth.AuthAPI
import com.kualagames.shared.components.auth.toCredentials
import com.kualagames.shared.components.auth.toProfile
import com.kualagames.shared.components.register.RegisterComponent.State
import com.kualagames.shared.components.register.RegisterStore.Intent
import com.kualagames.shared.components.register.RegisterStore.Label
import com.kualagames.shared.network.successBody
import com.kualagames.shared.storages.UserInfoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterStoreProvider(
    private val storeFactory: StoreFactory,
    private val api: AuthAPI,
    private val userInfoRepository: UserInfoRepository,
) {

    fun provide(): RegisterStore =
        object : RegisterStore, Store<Intent, State, Label> by storeFactory.create(
            name = "LoginStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Message {
        object Loading : Message
        object StopLoading : Message

        object ClearErrors : Message

        object RegisterFailed : Message

        object BadPassword : Message
        object BadUsername : Message
        object BadEmail : Message

        object DuplicateEmail : Message
        object DuplicateUsername : Message
    }

    // Logic should take place in the executor
    private inner class ExecutorImpl : CoroutineExecutor<Intent, Nothing, State, Message, Label>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.Register -> register(intent)
            }
        }

        private fun register(intent: Intent.Register) {
            scope.launch {
                if (intent.username.isBlank()) {
                    dispatch(Message.BadUsername)
                    delay(3000)
                    dispatch(Message.ClearErrors)
                    return@launch
                }
                if (intent.password.isBlank()) {
                    dispatch(Message.BadPassword)
                    delay(3000)
                    dispatch(Message.ClearErrors)
                    return@launch
                }
                if (intent.email.isBlank()) {
                    dispatch(Message.BadEmail)
                    delay(3000)
                    dispatch(Message.ClearErrors)
                    return@launch
                }

                dispatch(Message.Loading)

                val response = api.register(
                    email = intent.email,
                    username = intent.username,
                    password = intent.password
                )

                if (response.isSuccessful) {
                    val body = response.successBody

                    userInfoRepository.store(body.toCredentials(), body.toProfile())

                    publish(Label.Success)
                } else {

                    dispatch(Message.StopLoading)
                    dispatch(Message.RegisterFailed)
                    delay(3000)
                    dispatch(Message.ClearErrors)
                }
            }
        }
    }

    // The reducer processes the result and returns the new state
    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                is Message.Loading -> copy(loading = true)
                Message.StopLoading -> copy(loading = false)
                Message.ClearErrors -> copy(
                    showRegisterFailed = false,
                    showEmailError = false,
                    showUsernameError = false,
                    showPasswordError = false,
                    emailErrorReason = State.ErrorReason.No,
                    usernameErrorReason = State.ErrorReason.No,
                )

                Message.RegisterFailed -> copy(showRegisterFailed = true)

                Message.BadPassword -> copy(showPasswordError = true)
                Message.BadUsername -> copy(showUsernameError = true, usernameErrorReason = State.ErrorReason.Bad)
                Message.BadEmail -> copy(showEmailError = true, emailErrorReason = State.ErrorReason.Bad)

                Message.DuplicateEmail -> copy(showEmailError = true, emailErrorReason = State.ErrorReason.Duplicate)
                Message.DuplicateUsername -> copy(showUsernameError = true, usernameErrorReason = State.ErrorReason.Duplicate)
            }
    }
}