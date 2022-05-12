package com.kualagames.shared.components.room_name_picker

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.kualagames.shared.components.DIComponent
import com.kualagames.shared.components.room_name_picker.RoomNamePickerStore.Intent
import com.kualagames.shared.utils.addTo
import com.kualagames.shared.utils.asValue
import com.kualagames.shared.utils.createdDisposables
import com.kualagames.shared.utils.onNextLabel
import org.koin.core.component.get
import org.koin.core.scope.Scope

interface RoomNamePickerComponent {

    val state : Value<State>

    data class State(
        val nameError: RoomNameError = RoomNameError.No,
        val loading : Boolean = false,
        val nameCheckFailed : Boolean = false,
    ) {
        enum class RoomNameError {
            No,
            PickAName,
            Duplicate,
        }
    }

    fun onCreateRoomClicked(roomName: String)
}

class RoomNamePickerComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
    private val createRoom: (String, String) -> Unit,
) : DIComponent(componentContext, parentScope, listOf(roomNamePickerModule)), RoomNamePickerComponent {

    private val store: RoomNamePickerStore = instanceKeeper.getStore {
        get<RoomNamePickerStore>()
    }.apply {
        onNextLabel {
            when (it) {
                is RoomNamePickerStore.Label.CreateRoom -> {
                    createRoom(it.roomName, it.gameModeId)
                }
            }
        } addTo createdDisposables
    }
    override val state: Value<RoomNamePickerComponent.State> =
        store.asValue()

    override fun onCreateRoomClicked(roomName: String) {
        store.accept(Intent.CreateRoom(roomName))
    }
}