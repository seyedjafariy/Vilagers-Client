package com.kualagames.shared.components.rooms

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
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
    )

    fun onNewRoomClicked()
}

class RoomsComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
    openRoomNamePicker: () -> Unit,
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

    override val state: Value<RoomsComponent.State> =
        store.asValue()

    override fun onNewRoomClicked() {
        store.accept(Intent.NewRoom)
    }
}