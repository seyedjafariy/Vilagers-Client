package com.kualagames.shared.components.room_name_picker

import com.arkivanov.mvikotlin.core.store.Store
import com.kualagames.shared.components.room_name_picker.RoomNamePickerStore.Intent
import com.kualagames.shared.components.room_name_picker.RoomNamePickerStore.Label
import com.kualagames.shared.components.room_name_picker.RoomNamePickerComponent.State

interface RoomNamePickerStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class CreateRoom(val roomName: String) : Intent
    }

    sealed interface Label {
        data class CreateRoom(val roomName : String) : Label
    }
}