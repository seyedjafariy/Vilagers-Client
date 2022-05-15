package com.kualagames.shared.components.game.model

import com.kualagames.shared.components.auth.UserDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
@Serializable
sealed class CommandTypeDTO<T> {

    @SerialName("command")
    val command : Map<String, T>

    @Serializable
    @SerialName("game_command_agora_token")
    data class AgoraToken(
        @SerialName("command")
        val command: Command,
    ) : CommandTypeDTO() {

        @Serializable
        data class Command(
            @SerialName("token")
            val token: String,
        )
    }

    @Serializable
    @SerialName("game_command_waiting_for_others")
    data class WaitingForOthers(
        @SerialName("command")
        val command: Command,
    ) : CommandTypeDTO() {

        @Serializable
        data class Command(
            @SerialName("users")
            val users: List<UserDTO>,
        )
    }

    @Serializable
    @SerialName("game_command_all_players")
    data class AllPlayers(
        val users: List<GameUserDto>,
    ) : CommandTypeDTO()

    @Serializable
    data class RevealPlayerRole(
        val player: PublicUser,
        val role: Role,
    ) : CommandTypeDTO("current_user_role")

    @Serializable
    object Speak : CommandTypeDTO("user_speak")

    @Serializable
    object Silent : CommandTypeDTO("user_silent")

    @Serializable
    object AllowVoting : CommandTypeDTO("allow_voting")

    @Serializable
    object BlockActions : CommandTypeDTO("block_actions")

    @Serializable
    data class ShowVotes(
        val voting_for: String,
        val voted_users: List<String>,
    ) : CommandTypeDTO("block_voting")

    @Serializable
    object ClearAllVotes : CommandTypeDTO("clear_all_votes")

    @Serializable
    object ShowDay : CommandTypeDTO("show_day")

    @Serializable
    object ShowNight : CommandTypeDTO("show_night")

    @Serializable
    object VoteDropped : CommandTypeDTO("vote_dropped")

    @Serializable
    data class PlayerVotedOut(
        val player: PublicUser,
    ) : CommandTypeDTO("player_voted_out")

    @Serializable
    data class PlayerKilled(
        val player: PublicUser,
    ) : CommandTypeDTO("player_killed")

    @Serializable
    object AllowShooting : CommandTypeDTO("allow_shooting")

    @Serializable
    object MakeYourMove : CommandTypeDTO("make_your_move")

    @Serializable
    data class RevealPlayerSide(
        val player: PublicUser,
        val is_villager: Boolean,
        val is_werewolf: Boolean,
    ) : CommandTypeDTO("reveal_player_side")

    @Serializable
    data class GameOver(
        val winner_side: Side,
        val all_players_roles: List<RevealPlayerRole>,
    ) : CommandTypeDTO("game_over")
}

 */