package com.kualagames.shared.components.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
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
import org.koin.core.scope.Scope

interface MainComponent {

    val routerState: Value<RouterState<*, Child>>

    sealed interface Child {
        class Rooms(val component: RoomsComponent) : Child
        class RoomNamePicker(val component: RoomNamePickerComponent) : Child
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
            Config.Rooms -> Child.Rooms(RoomsComponentImpl(componentContext, scope) {
                router.push(Config.RoomNamePicker)
            })
            Config.RoomNamePicker -> Child.RoomNamePicker(RoomNamePickerComponentImpl(componentContext, scope){
                //TODO route to Waiting room
            })
        }

    private sealed interface Config : Parcelable {

        @Parcelize
        object Rooms : Config

        @Parcelize
        object RoomNamePicker : Config
    }
}