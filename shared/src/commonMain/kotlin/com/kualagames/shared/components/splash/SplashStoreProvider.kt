package com.kualagames.shared.components.splash

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kualagames.shared.components.splash.SplashComponent.State
import com.kualagames.shared.storages.UserInfoRepository
import com.kualagames.shared.storages.userLoaded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashStoreProvider(
    private val storeFactory: StoreFactory,
    private val userInfoRepository: UserInfoRepository,
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
                val userExists = withContext(Dispatchers.Default) {
                    userInfoRepository.loadFromStorage()
                    userInfoRepository.userLoaded
                }

                if (userExists) {
                    publish(State.Main)
                } else {
                    publish(State.Auth)
                }
            }
        }
    }
}