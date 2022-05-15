package com.kualagames.shared.components.waiting_room

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kualagames.shared.components.auth.toProfile
import com.kualagames.shared.components.rooms.Room
import com.kualagames.shared.components.rooms.RoomDTO
import com.kualagames.shared.components.waiting_room.WaitingRoomComponent.State
import com.kualagames.shared.components.waiting_room.WaitingRoomStore.Intent
import com.kualagames.shared.components.waiting_room.WaitingRoomStore.Label
import com.kualagames.shared.model.Profile
import com.kualagames.shared.utils.exhaustive
import kotlinx.coroutines.launch

class WaitingRoomStoreProvider(
    private val storeFactory: StoreFactory,
    private val roomManager: RoomManager,
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
        data class RoomName(val name: String) : Message
        data class NewUpdate(val update: String) : Message
        data class UserUpdate(val users: List<Profile>) : Message
        object ShowStartButton : Message
        object EnableStartButton : Message
        object DisableStartButton : Message
    }

    sealed interface Action {

        sealed interface Connection : Action {
            data class Create(val roomName: String, val gameModeId: String) : Connection
            data class Join(val roomName: String) : Connection
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Message, Label>() {

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.Connection.Create -> {
                    dispatch(Message.RoomName(action.roomName))
                    dispatch(Message.ShowStartButton)
                    createRoom(action)
                }
                is Action.Connection.Join -> {
                    dispatch(Message.RoomName(action.roomName))
                    joinRoom(action)
                }
            }.exhaustive
        }

        private fun createRoom(action: Action.Connection.Create) {
            scope.launch {
                roomManager.createNewAndObserve(action.roomName, action.gameModeId)
                    .collect {
                        if (Room.Status(it.status) == Room.Status.Filled) {
                            dispatch(Message.EnableStartButton)
                        } else {
                            dispatch(Message.DisableStartButton)
                        }

                        updateRoomDetails(it)
                    }
            }
        }

        private fun joinRoom(action: Action.Connection.Join) {
            scope.launch {
                roomManager.joinAndObserve(action.roomName)
                    .collect {
                        updateRoomDetails(it)
                    }
            }
        }

        private fun updateRoomDetails(it: RoomDTO) {
            dispatch(Message.UserUpdate(it.users.map { it.toProfile() }))
            dispatch(Message.NewUpdate(it.toString()))

            if (Room.Status(it.status) == Room.Status.Started) {
                publish(Label.OpenGame(it.gameId!!))
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.StartGame -> {
                    scope.launch {
                        roomManager.send(WaitingRoomCommand(type = WaitingRoomCommand.Type.StartGame.raw))
                    }
                }
            }.exhaustive
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State {
            return when (msg) {
                Message.ShowStartButton -> copy(showStartButton = true)
                Message.EnableStartButton -> copy(enableStartButton = true)
                Message.DisableStartButton -> copy(enableStartButton = false)
                is Message.NewUpdate -> copy(remoteMessages = this.remoteMessages + "\n" + msg.update)
                is Message.RoomName -> copy(roomName = roomName)
                is Message.UserUpdate -> copy(users = msg.users.map { it.username })
            }
        }
    }
}