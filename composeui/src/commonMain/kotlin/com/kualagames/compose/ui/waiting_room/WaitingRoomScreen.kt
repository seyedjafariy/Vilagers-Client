package com.kualagames.compose.ui.waiting_room

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.kualagames.shared.components.waiting_room.WaitingRoomComponent
import com.kualagames.shared.components.waiting_room.WaitingRoomComponent.State

@Composable
fun WaitingRoomScreen(component: WaitingRoomComponent) {
    val state by component.state.subscribeAsState()
    InternalWaitingRoomScreen(state, component)
}

@Composable
private fun InternalWaitingRoomScreen(state: State, component: WaitingRoomComponent) = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {

    Text(state.remoteMessages)
}