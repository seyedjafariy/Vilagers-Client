package com.kualagames.shared.components.waiting_room

import com.arkivanov.mvikotlin.core.store.Store
import com.kualagames.shared.components.waiting_room.WaitingRoomStore.Intent
import com.kualagames.shared.components.waiting_room.WaitingRoomStore.Label
import com.kualagames.shared.components.waiting_room.WaitingRoomComponent.State

interface WaitingRoomStore : Store<Intent, WaitingRoomComponent.State, Label> {
    sealed interface Intent {
    }

    sealed interface Label {
    }
}