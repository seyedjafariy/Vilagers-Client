package com.kualagames.shared.components.waiting_room

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WaitingRoomCommand(
    @SerialName("type")
    val type: String = Type.StartGame.raw,
) {

    enum class Type(val raw: String) {
        StartGame("start_game")
    }
}