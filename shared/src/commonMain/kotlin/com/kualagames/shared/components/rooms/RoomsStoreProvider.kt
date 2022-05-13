package com.kualagames.shared.components.rooms

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kualagames.shared.components.rooms.RoomsComponent.State
import com.kualagames.shared.components.rooms.RoomsStore.Intent
import com.kualagames.shared.components.rooms.RoomsStore.Label
import com.kualagames.shared.network.successBody
import kotlinx.coroutines.launch

class RoomsStoreProvider(
    private val storeFactory: StoreFactory,
    private val api: RoomsAPI,
) {

    fun provide(): RoomsStore =
        object : RoomsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "RoomsStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Action.LoadRooms),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        object LoadRooms : Action
    }

    private sealed interface Message {
        object Loading : Message
        object StopLoading : Message
        data class Loaded(val rooms: List<Room>) : Message

    }

    // Logic should take place in the executor
    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Message, Label>() {

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.LoadRooms -> {
                    loadRooms()
                }
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.NewRoom -> publish(Label.PickRoomName)
            }
        }

        private fun loadRooms() {
            scope.launch {
                dispatch(Message.Loading)

                val response = api.fetchWaitingRooms()

                dispatch(Message.StopLoading)
                if (response.isSuccessful) {
                    dispatch(Message.Loaded(response.successBody.map { it.toDomain() }))
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
                is Message.Loaded -> copy(rooms = msg.rooms)
            }
    }
}