package com.kualagames.shared.components.game.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class GameActionDTO(
    @SerialName("type")
    val rawType: String,
) {

    @Serializable
    data class VoteForPlayer(
        val user_id: String,
    ) : GameActionDTO("vote_for_player")

    @Serializable
    data class ShotPlayer(
        val user_id: String,
    ) : GameActionDTO("shot_player")

    @Serializable
    data class RoleMove(
        val user_id : String?,
    ): GameActionDTO("role_move")
}