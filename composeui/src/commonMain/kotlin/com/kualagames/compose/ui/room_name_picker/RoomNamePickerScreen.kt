package com.kualagames.compose.ui.room_name_picker

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.kualagames.shared.components.room_name_picker.RoomNamePickerComponent
import com.kualagames.shared.components.room_name_picker.RoomNamePickerComponent.State

@Composable
fun RoomNamePickerScreen(component: RoomNamePickerComponent) {
    val state by component.state.subscribeAsState()
    InternalRoomNamePickerScreen(state, component)

}

@Composable
private fun InternalRoomNamePickerScreen(state: State, component: RoomNamePickerComponent) =
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        var nameState by remember { mutableStateOf("") }

        TextField(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            value = nameState,
            onValueChange = {
                nameState = it
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.loading) {
            CircularProgressIndicator()
        } else if (state.nameError != State.RoomNameError.No) {
            when (state.nameError) {
                State.RoomNameError.No -> {
                    //no-op
                }
                State.RoomNameError.PickAName -> {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        text = "Please type a valid name"
                    )
                }
                State.RoomNameError.Duplicate -> {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        text = "This name exists please pick another name"
                    )
                }
            }
        } else {
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                onClick = {
                    component.onCreateRoomClicked(nameState)
                }) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center,
                    text = "Create Room"
                )
            }
        }
    }