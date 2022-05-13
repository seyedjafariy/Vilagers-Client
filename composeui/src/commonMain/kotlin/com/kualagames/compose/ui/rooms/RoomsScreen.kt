@file:OptIn(ExperimentalUnitApi::class)

package com.kualagames.compose.ui.rooms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.kualagames.shared.components.rooms.Room
import com.kualagames.shared.components.rooms.RoomsComponent
import com.kualagames.shared.components.rooms.RoomsComponent.State

@Composable
fun RoomsScreen(component: RoomsComponent) {
    val state by component.state.subscribeAsState()
    InternalRoomsScreen(state, component)
}

@Composable
fun InternalRoomsScreen(state: State, component: RoomsComponent) = Box(Modifier.fillMaxSize()) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("ROOMS", modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(state.rooms, { it.name }) {
                RoomRow(it){
                    component.onRoomClicked(it)
                }
            }
        }
    }

    FloatingActionButton(
        modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp),
        onClick = {
            component.onNewRoomClicked()
        },
    ) {
        Text("New")
    }
}

@Composable
private fun RoomRow(it: Room, click: (Room) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable { click(it) }) {
        Text(it.name, fontSize = TextUnit(24F, TextUnitType.Sp))
        Text("players: ${it.playerCount}", fontSize = TextUnit(16F, TextUnitType.Sp))
    }
}