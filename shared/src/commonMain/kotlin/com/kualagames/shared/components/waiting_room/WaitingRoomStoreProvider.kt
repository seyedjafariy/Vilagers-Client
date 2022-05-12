package com.kualagames.shared.components.waiting_room

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kualagames.shared.components.rooms.RoomsAPI
import com.kualagames.shared.components.waiting_room.WaitingRoomComponent.State
import com.kualagames.shared.components.waiting_room.WaitingRoomStore.Intent
import com.kualagames.shared.components.waiting_room.WaitingRoomStore.Label
import kotlinx.coroutines.launch

class WaitingRoomStoreProvider(
    private val storeFactory: StoreFactory,
    private val roomsAPI: RoomsAPI,
) {

    fun provide(connection: Action.Connection): WaitingRoomStore =
        object : WaitingRoomStore, Store<Intent, State, Label>
        by storeFactory.create(
            name = "WaitingRoomStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(connection),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    sealed interface Message {
        data class NewUpdate(val update: String) : Message
    }

    sealed interface Action {

        sealed interface Connection : Action {
            data class Create(val roomName: String, val gameModeId: String) : Connection
            data class Join(val roomName: String) : Connection
        }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Action, State, Message, Label>() {

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.Connection.Create -> {
                    createRoom(action)
                }
                is Action.Connection.Join -> {
                    TODO()
                }
            }
        }

        private fun createRoom(action: Action.Connection.Create) {
            scope.launch {
                roomsAPI.createRoom(action.roomName, action.gameModeId)
                    .collect {
                        dispatch(Message.NewUpdate(it))
                    }
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State {
            return when (msg) {
                is Message.NewUpdate -> copy(remoteMessages = this.remoteMessages + "\n" + msg.update)
            }
        }
    }
}