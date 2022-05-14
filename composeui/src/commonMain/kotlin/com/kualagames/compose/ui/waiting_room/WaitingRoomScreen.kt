@file:OptIn(ExperimentalUnitApi::class, ExperimentalUnitApi::class)

package com.kualagames.compose.ui.waiting_room

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
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

    Column(Modifier.fillMaxWidth()) {
        Text(state.roomName, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            val usersIndex = state.users.withIndex().toList()

            items(
                items = usersIndex,
                key = { it.value },
            ) {
                Text("#${it.index}: ${it.value}")
            }
        }

    }
    if (state.showStartButton)
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter),
            enabled = state.enableStartButton,
            onClick = {
                component.onStartGameClicked()
            }
        ) {
            Text("Start Game", fontSize = TextUnit(16F, TextUnitType.Sp))
        }
}