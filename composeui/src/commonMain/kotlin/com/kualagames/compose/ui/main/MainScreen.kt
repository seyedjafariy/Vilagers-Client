package com.kualagames.compose.ui.main

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.kualagames.compose.ui.game.GameScreen
import com.kualagames.compose.ui.room_name_picker.RoomNamePickerScreen
import com.kualagames.compose.ui.rooms.RoomsScreen
import com.kualagames.compose.ui.waiting_room.WaitingRoomScreen
import com.kualagames.shared.components.main.MainComponent
import com.kualagames.shared.utils.exhaustive

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun MainScreen(component: MainComponent) = Children(component.routerState) {
    when (val child = it.instance) {
        is MainComponent.Child.Rooms -> RoomsScreen(child.component)
        is MainComponent.Child.RoomNamePicker -> RoomNamePickerScreen(child.component)
        is MainComponent.Child.WaitingRoom -> WaitingRoomScreen(child.component)
        is MainComponent.Child.Game -> GameScreen(child.component)
    }.exhaustive
}
