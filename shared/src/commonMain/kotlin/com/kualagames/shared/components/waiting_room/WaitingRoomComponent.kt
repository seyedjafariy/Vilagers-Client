package com.kualagames.shared.components.waiting_room

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.kualagames.shared.components.DIComponent
import com.kualagames.shared.components.waiting_room.WaitingRoomComponent.Input
import com.kualagames.shared.components.waiting_room.WaitingRoomStoreProvider.Action.Connection
import com.kualagames.shared.utils.addTo
import com.kualagames.shared.utils.asValue
import com.kualagames.shared.utils.createdDisposables
import com.kualagames.shared.utils.onNextLabel
import org.koin.core.component.get
import org.koin.core.scope.Scope

interface WaitingRoomComponent {

    val state: Value<State>

    data class State(
        val roomName : String = "",
        val remoteMessages: String = "start",
        val users : List<String> = emptyList(),
        val showStartButton : Boolean = false,
        val enableStartButton : Boolean = false,
    )

    sealed interface Input : Parcelable {
        @Parcelize
        data class CreateNewRoom(
            val roomName: String,
            val gameModeId: String,
        ) : Input

        @Parcelize
        data class JoinRoom(
            val roomName: String,
        ) : Input
    }

    fun onStartGameClicked()
}

class WaitingRoomComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
    input: Input
) : DIComponent(componentContext, parentScope, listOf(waitingRoomModule(input.toConnection()))), WaitingRoomComponent {

    private val store: WaitingRoomStore = instanceKeeper.getStore {
        get<WaitingRoomStore>()
    }.apply {
        onNextLabel {

        } addTo createdDisposables
    }
    override val state: Value<WaitingRoomComponent.State> =
        store.asValue()

    override fun onStartGameClicked() {
        store.accept(WaitingRoomStore.Intent.StartGame)
    }
}

private fun Input.toConnection() =
    when (this) {
        is Input.CreateNewRoom -> Connection.Create(this.roomName, this.gameModeId)
        is Input.JoinRoom -> Connection.Join(this.roomName)
    }
