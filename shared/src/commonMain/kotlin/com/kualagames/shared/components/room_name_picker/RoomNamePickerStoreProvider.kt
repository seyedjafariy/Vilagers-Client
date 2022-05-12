package com.kualagames.shared.components.room_name_picker

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.kualagames.shared.components.room_name_picker.RoomNamePickerStore.Intent
import com.kualagames.shared.components.room_name_picker.RoomNamePickerStore.Label
import com.kualagames.shared.components.room_name_picker.RoomNamePickerComponent.State
import com.kualagames.shared.components.rooms.RoomsAPI
import com.kualagames.shared.network.successBody
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RoomNamePickerStoreProvider(
    private val storeFactory: StoreFactory,
    private val api: RoomsAPI,
) {

    fun provide(): RoomNamePickerStore =
        object : RoomNamePickerStore, Store<Intent, State, Label>
        by storeFactory.create(
            name = "RoomNamePickerStore",
            initialState = State(),
            bootstrapper = null,
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    sealed interface Message {
        object NoName : Message
        object ClearErrors : Message
        object Loading : Message
        object StopLoading : Message
        object Failed : Message
        object NameExists : Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Nothing, State, Message, Label>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.CreateRoom -> checkRoomName(intent.roomName)
            }
        }

        private fun checkRoomName(name: String) {
            scope.launch {
                if (name.trim().isBlank()) {
                    dispatch(Message.NoName)

                    delay(3000)

                    dispatch(Message.ClearErrors)
                    return@launch
                }

                dispatch(Message.Loading)

                val roomsResponse = api.fetchWaitingRooms()

                if (roomsResponse.isNotSuccessful) {
                    dispatch(Message.StopLoading)
                    dispatch(Message.Failed)
                    delay(3000)
                    dispatch(Message.ClearErrors)
                    return@launch
                }

                val rooms = roomsResponse.successBody

                val duplicateName = rooms.any { it.roomName == name }

                if (duplicateName) {
                    dispatch(Message.StopLoading)
                    dispatch(Message.NameExists)
                    delay(3000)
                    dispatch(Message.ClearErrors)
                    return@launch
                }

                dispatch(Message.StopLoading)
                publish(Label.CreateRoom(name))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State {
            return when (msg) {
                Message.NoName -> copy(nameError = State.RoomNameError.PickAName)
                Message.NameExists -> copy(nameError = State.RoomNameError.Duplicate)
                Message.ClearErrors -> copy(nameError = State.RoomNameError.No, nameCheckFailed = false)
                Message.Loading -> copy(loading = true)
                Message.StopLoading -> copy(loading = false)
                Message.Failed -> copy(nameCheckFailed = true)
            }
        }
    }
}