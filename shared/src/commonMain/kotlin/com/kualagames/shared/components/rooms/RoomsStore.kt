package com.kualagames.shared.components.rooms

import com.arkivanov.mvikotlin.core.store.Store
import com.kualagames.shared.components.rooms.RoomsStore.Intent
import com.kualagames.shared.components.rooms.RoomsComponent.State

interface RoomsStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
    }
}