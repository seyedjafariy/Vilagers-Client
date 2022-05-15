package com.kualagames.shared.components.game

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.kualagames.shared.components.DIComponent
import com.kualagames.shared.components.waiting_room.WaitingRoomComponent
import com.kualagames.shared.components.waiting_room.WaitingRoomStore
import com.kualagames.shared.utils.addTo
import com.kualagames.shared.utils.asValue
import com.kualagames.shared.utils.createdDisposables
import com.kualagames.shared.utils.onNextLabel
import org.koin.core.component.get
import org.koin.core.scope.Scope

interface GameComponent {

    val state: Value<State>

    data class State(
        val waitingToStartTheGame : Boolean = true
    )

    data class Input(
        val gameId: String
    )
}

class GameComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
    input: GameComponent.Input,
) : DIComponent(componentContext, parentScope, listOf(gameModule(input.toAction()))), GameComponent {

    private val store: GameStore = instanceKeeper.getStore {
        get<GameStore>()
    }.apply {
        onNextLabel { label ->

        } addTo createdDisposables
    }
    override val state: Value<GameComponent.State> =
        store.asValue()
}

fun GameComponent.Input.toAction() = GameStore.Action.GameId(
    this.gameId
)