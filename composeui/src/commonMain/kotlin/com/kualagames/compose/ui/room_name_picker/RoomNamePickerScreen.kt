package com.kualagames.compose.ui.room_name_picker

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kualagames.shared.components.room_name_picker.RoomNamePickerComponent

@Composable
fun RoomNamePickerScreen(component: RoomNamePickerComponent) = Column(verticalArrangement = Arrangement.Center) {

    var nameState by remember { mutableStateOf("") }

    TextField(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        value = nameState,
        onValueChange = {
            nameState = it
        }
    )

    Spacer(modifier = Modifier.height(16.dp))

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