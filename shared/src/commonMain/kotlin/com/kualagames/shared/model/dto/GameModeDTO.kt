package com.kualagames.shared.model.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameModeDTO(
    @SerialName("id")
    val id: String,
    @SerialName("desc")
    val desc: String,
    @SerialName("difficulty")
    val difficulty: String,
    @SerialName("name")
    val name: String,
    @SerialName("noPlayers")
    val noPlayers: Int,
    @SerialName("roles")
    val roles : List<RoleDTO>,
)