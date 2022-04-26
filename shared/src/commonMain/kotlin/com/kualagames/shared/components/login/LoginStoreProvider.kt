package com.kualagames.shared.components.login

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kualagames.shared.components.auth.AuthAPI
import com.kualagames.shared.components.auth.toCredentials
import com.kualagames.shared.components.auth.toProfile
import com.kualagames.shared.components.login.LoginComponent.State
import com.kualagames.shared.components.login.LoginStore.Intent
import com.kualagames.shared.components.login.LoginStore.Label
import com.kualagames.shared.network.successBody
import com.kualagames.shared.storages.UserInfoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginStoreProvider(
    private val storeFactory: StoreFactory,
    private val api: AuthAPI,
    private val userInfoRepository: UserInfoRepository,
) {

    fun provide(): LoginStore =
        object : LoginStore, Store<Intent, State, Label> by storeFactory.create(
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

        object LoginFailed : Message
        object WrongPassword : Message
        object WrongUsername : Message
    }

    // Logic should take place in the executor
    private inner class ExecutorImpl : CoroutineExecutor<Intent, Nothing, State, Message, Label>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.Login -> login(intent)
            }
        }

        private fun login(intent: Intent.Login) {
            scope.launch {
                if (intent.username.isBlank()) {
                    dispatch(Message.WrongUsername)
                    delay(3000)
                    dispatch(Message.ClearErrors)
                    return@launch
                }
                if (intent.password.isBlank()) {
                    dispatch(Message.WrongPassword)
                    delay(3000)
                    dispatch(Message.ClearErrors)
                    return@launch
                }

                dispatch(Message.Loading)

                val response = api.login(intent.username, intent.password)

                if (response.isSuccessful) {
                    val body = response.successBody

                    userInfoRepository.store(body.toCredentials(), body.toProfile())

                    publish(Label.Success)
                } else {

                    dispatch(Message.StopLoading)
                    dispatch(Message.LoginFailed)
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
                Message.ClearErrors -> copy(showLoginFailed = false, showWrongPass = false, showWrongUsername = false)
                Message.LoginFailed -> copy(showLoginFailed = true)
                Message.WrongPassword -> copy(showWrongPass = true)
                Message.WrongUsername -> copy(showWrongUsername = true)
            }
    }
}