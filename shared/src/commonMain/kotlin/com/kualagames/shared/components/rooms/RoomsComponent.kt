package com.kualagames.shared.components.rooms

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.subscribe
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.kualagames.shared.components.DIComponent
import com.kualagames.shared.components.rooms.RoomsStore.Intent
import com.kualagames.shared.utils.addTo
import com.kualagames.shared.utils.asValue
import com.kualagames.shared.utils.createdDisposables
import com.kualagames.shared.utils.onNextLabel
import org.koin.core.component.get
import org.koin.core.scope.Scope

interface RoomsComponent {

    val state: Value<State>

    data class State(
        val loading: Boolean = false,
        val user: String = "",
        val rooms: List<Room> = emptyList(),
    )

    fun onNewRoomClicked()
    fun onRoomClicked(room: Room)
}

class RoomsComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
    openRoomNamePicker: () -> Unit,
    private val openWaitingRoom: (Room) -> Unit,
) : DIComponent(componentContext, parentScope, listOf(roomsModule)), RoomsComponent {

    private val store: RoomsStore = instanceKeeper.getStore {
        get<RoomsStore>()
    }.apply {
        onNextLabel {
            when (it) {
                RoomsStore.Label.PickRoomName -> {
                    openRoomNamePicker()
                }
            }
        } addTo createdDisposables
    }

    init {
        lifecycle.subscribe(onResume = ::onResume)
    }

    override val state: Value<RoomsComponent.State> =
        store.asValue()

    private fun onResume() {
        store.accept(Intent.Update)
    }

    override fun onNewRoomClicked() {
        store.accept(Intent.NewRoom)
    }

    override fun onRoomClicked(room: Room) {
        openWaitingRoom(room)
    }
}