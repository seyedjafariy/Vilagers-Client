package com.kualagames.shared.components.splash

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kualagames.shared.components.auth.AuthConst
import com.kualagames.shared.components.splash.SplashComponent.State
import com.kualagames.shared.settings.SettingsStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashStoreProvider(
    private val storeFactory: StoreFactory,
    private val settingsStorage: SettingsStorage,
) {

    fun provide(): SplashStore =
        object : SplashStore, Store<Nothing, Any, State> by storeFactory.create(
            name = "MainStore",
            initialState = Any(),
            bootstrapper = SimpleBootstrapper(Actions.Start),
            executorFactory = ::ExecutorImpl,
        ) {}

    private sealed interface Actions {
        object Start : Actions
    }

    // Logic should take place in the executor
    private inner class ExecutorImpl : CoroutineExecutor<Nothing, Actions, Any, Nothing, State>() {

        // Action: Called once by the bootstrapper
        override fun executeAction(action: Actions, getState: () -> Any) {
            when (action) {
                Actions.Start -> {
                    fetchUserState()
                }
            }
        }

        private fun fetchUserState() {
            scope.launch {
                val token = withContext(Dispatchers.Default) {
                    settingsStorage.get(AuthConst.SETTINGS_KEY_USER_TOKEN)
                }

                if (token != null) {
                    publish(State.Main)
                } else {
                    publish(State.Auth)
                }
            }
        }
    }
}