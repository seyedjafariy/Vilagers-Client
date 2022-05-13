package com.kualagames.shared.components.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.navigate
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.kualagames.shared.components.DIComponent
import com.kualagames.shared.components.main.MainComponent.Child
import com.kualagames.shared.components.room_name_picker.RoomNamePickerComponent
import com.kualagames.shared.components.room_name_picker.RoomNamePickerComponentImpl
import com.kualagames.shared.components.rooms.RoomsComponent
import com.kualagames.shared.components.rooms.RoomsComponentImpl
import com.kualagames.shared.components.waiting_room.WaitingRoomComponent
import com.kualagames.shared.components.waiting_room.WaitingRoomComponentImpl
import org.koin.core.scope.Scope

interface MainComponent {

    val routerState: Value<RouterState<*, Child>>

    sealed interface Child {
        class Rooms(val component: RoomsComponent) : Child
        class RoomNamePicker(val component: RoomNamePickerComponent) : Child
        class WaitingRoom(val component: WaitingRoomComponent) : Child
    }
}

class MainComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
) : DIComponent(componentContext, parentScope), MainComponent {

    private val router = router<Config, Child>(
        initialConfiguration = Config.Rooms,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val routerState: Value<RouterState<*, Child>> =
        router.state

    private fun createChild(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            Config.Rooms -> Child.Rooms(RoomsComponentImpl(componentContext, scope, {
                router.push(Config.RoomNamePicker)
            }) {
                router.push(Config.WaitingRoom(WaitingRoomComponent.Input.JoinRoom(it.name)))
            })
            Config.RoomNamePicker -> Child.RoomNamePicker(
                RoomNamePickerComponentImpl(
                    componentContext,
                    scope
                ) { roomName, gameModeId ->
                    router.navigate {
                        val mutableConfigs = it.toMutableList()
                        mutableConfigs.remove(Config.RoomNamePicker)
                        mutableConfigs.add(Config.WaitingRoom(WaitingRoomComponent.Input.CreateNewRoom(roomName, gameModeId)))
                        mutableConfigs
                    }
                })
            is Config.WaitingRoom -> Child.WaitingRoom(WaitingRoomComponentImpl(componentContext, scope, config.input))
        }

    private sealed interface Config : Parcelable {

        @Parcelize
        object Rooms : Config

        @Parcelize
        object RoomNamePicker : Config

        @Parcelize
        data class WaitingRoom(val input: WaitingRoomComponent.Input) : Config
    }
}