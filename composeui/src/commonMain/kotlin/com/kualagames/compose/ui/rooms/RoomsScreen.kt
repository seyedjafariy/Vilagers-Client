package com.kualagames.compose.ui.rooms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kualagames.shared.components.rooms.RoomsComponent

@Composable
fun RoomsScreen(component: RoomsComponent) = Box(Modifier.fillMaxSize()) {

    FloatingActionButton(
        modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp),
        onClick = {
            component.onNewRoomClicked()
        },
    ) {
        Text("New")
    }
}