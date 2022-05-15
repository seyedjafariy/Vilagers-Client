package com.kualagames.shared.components.game.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SideDTO(
    val sideName: String,
) {

    @SerialName("villager")
    Villager("villager"),

    @SerialName("werewolf")
    Werewolf("werewolf")
}