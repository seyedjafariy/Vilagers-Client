package com.kualagames.shared.components.room_name_picker

import com.arkivanov.decompose.ComponentContext
import com.kualagames.shared.components.DIComponent
import org.koin.core.scope.Scope

interface RoomNamePickerComponent {

    fun onCreateRoomClicked(roomName: String)
}

class RoomNamePickerComponentImpl(
    componentContext: ComponentContext,
    parentScope: Scope,
) : DIComponent(componentContext, parentScope), RoomNamePickerComponent {
    override fun onCreateRoomClicked(roomName: String) {
        //no need for a store in this component
        //validate the name here
        //call some callback on the component and change the screen using the parent component
        TODO("Not yet implemented")
    }
}