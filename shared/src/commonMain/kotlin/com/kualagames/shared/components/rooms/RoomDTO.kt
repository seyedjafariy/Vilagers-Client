package com.kualagames.shared.components.rooms

import com.kualagames.shared.components.auth.UserDTO
import com.kualagames.shared.model.dto.GameModeDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomDTO(
    @SerialName("roomName")
    val roomName: String,
    @SerialName("roomHost")
    val roomHost: UserDTO,
    @SerialName("gameMode")
    val gameMode: GameModeDTO,
    @SerialName("users")
    val users: List<UserDTO>,
    @SerialName("status")
    val status: String,
    @SerialName("gameId")
    val gameId: String? = null,
)

