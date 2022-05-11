package com.kualagames.shared.components.rooms

import com.kualagames.shared.components.auth.UserDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomDTO(
    @SerialName("roomName")
    val roomName: String,
    @SerialName("roomHost")
    val roomHost: UserDTO,
    @SerialName("users")
    val users: List<UserDTO>,
)
