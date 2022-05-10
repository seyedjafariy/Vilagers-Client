package com.kualagames.compose.ui.rooms

import androidx.compose.foundation.layout.Box
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kualagames.shared.components.rooms.RoomsComponent

@Composable
fun RoomsScreen(component: RoomsComponent) = Box {



    FloatingActionButton(
        modifier = Modifier.align(Alignment.BottomEnd),
        onClick = {
            component.onNewRoomClicked()
        },
    ) {
        Text("New")
    }
}