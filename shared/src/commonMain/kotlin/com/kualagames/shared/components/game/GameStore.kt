package com.kualagames.shared.components.game

import com.arkivanov.mvikotlin.core.store.Store
import com.kualagames.shared.components.game.GameComponent.State
import com.kualagames.shared.components.game.GameStore.Intent
import com.kualagames.shared.components.game.GameStore.Label

interface GameStore : Store<Intent, State, Label> {

    sealed interface Action {
        data class GameId(val id: String) : Action
    }

    interface Intent {

    }

    interface Label {

    }
}