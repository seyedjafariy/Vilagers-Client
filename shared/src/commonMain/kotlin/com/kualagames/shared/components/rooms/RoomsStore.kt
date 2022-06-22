package com.kualagames.shared.components.rooms

import com.arkivanov.mvikotlin.core.store.Store
import com.kualagames.shared.components.rooms.RoomsStore.Intent
import com.kualagames.shared.components.rooms.RoomsStore.Label
import com.kualagames.shared.components.rooms.RoomsComponent.State

interface RoomsStore : Store<Intent, State, Label> {

    sealed interface Intent {
        object NewRoom : Intent
        object Update : Intent
    }

    sealed interface Label {
        object PickRoomName : Label
    }
}