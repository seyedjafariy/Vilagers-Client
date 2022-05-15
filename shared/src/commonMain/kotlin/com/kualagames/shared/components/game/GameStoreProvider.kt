package com.kualagames.shared.components.game

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kualagames.shared.components.game.GameComponent.State
import com.kualagames.shared.components.game.GameStore.*
import com.kualagames.shared.utils.exhaustive

class GameStoreProvider(
    private val storeFactory: StoreFactory,
    private val gameManager: GameManager,
) {

    fun provide(action: Action.GameId): GameStore = object : GameStore, Store<Intent, State, Label> by storeFactory.create(
        name = "GameStore",
        initialState = State(),
        bootstrapper = SimpleBootstrapper(action),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ) {}

    sealed interface Message {
        object WaitingForPlayers : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Message, Label>() {


        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.GameId -> {
                    gameManager.joinAndObserve(action.id)
                }
            }.exhaustive
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State = when (msg) {
            Message.WaitingForPlayers -> copy(waitingToStartTheGame = true)
        }
    }
}
