package com.kualagames.shared.components.login

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kualagames.shared.components.auth.AuthAPI
import com.kualagames.shared.components.login.LoginComponent.State
import com.kualagames.shared.components.login.LoginStore.Intent
import com.kualagames.shared.network.successData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginStoreProvider(
    private val storeFactory: StoreFactory,
    private val api: AuthAPI,
) {

    fun provide(): LoginStore =
        object : LoginStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "LoginStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Message {
        data class Loaded(
            val echo: String
        ) : Message
    }

    // Logic should take place in the executor
    private inner class ExecutorImpl : CoroutineExecutor<Intent, Nothing, State, Message, Nothing>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.Login -> login(intent)
            }
        }

        private fun login(intent: Intent.Login) {
            scope.launch {
                val response = withContext(Dispatchers.Default){
                    api.echo()
                }

                val echoData = response.successData().toList().joinToString(separator = "\n")

                dispatch(Message.Loaded(echoData))
            }
        }
    }

    // The reducer processes the result and returns the new state
    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                is Message.Loaded -> copy(loading = false, echoData = msg.echo)
            }
    }

}