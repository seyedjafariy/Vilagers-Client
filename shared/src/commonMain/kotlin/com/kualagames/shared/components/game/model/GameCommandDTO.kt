package com.kualagames.shared.components.game.model

import com.kualagames.shared.components.auth.UserDTO
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed class GameCommandDTO(
    @Transient
    val raw_type: String = "raw",
) {

    @Serializable
    data class AgoraToken(
        val token: String,
    ) : GameCommandDTO("agora_token")

    @Serializable
    data class WaitingForOthers(
        val users: List<PlayerDTO>,
    ) : GameCommandDTO("waiting_for_others")

    @Serializable
    data class AllPlayers(
        val users: List<PlayerDTO>,
    ) : GameCommandDTO("all_players")

    @Serializable
    data class RevealPlayerRole(
        val player: UserDTO,
        val role: RoleDTO,
    ) : GameCommandDTO("current_user_role")

    @Serializable
    object Speak : GameCommandDTO("user_speak")

    @Serializable
    object Silent : GameCommandDTO("user_silent")

    @Serializable
    object AllowVoting : GameCommandDTO("allow_voting")

    @Serializable
    object BlockActions : GameCommandDTO("block_actions")

    @Serializable
    data class ShowVotes(
        val voting_for: String,
        val voted_users: List<String>,
    ) : GameCommandDTO("block_voting")

    @Serializable
    object ClearAllVotes : GameCommandDTO("clear_all_votes")

    @Serializable
    object ShowDay : GameCommandDTO("show_day")

    @Serializable
    object ShowNight : GameCommandDTO("show_night")

    @Serializable
    object VoteDropped : GameCommandDTO("vote_dropped")

    @Serializable
    data class PlayerVotedOut(
        val player: PlayerDTO,
    ) : GameCommandDTO("player_voted_out")

    @Serializable
    data class PlayerKilled(
        val player: PlayerDTO,
    ) : GameCommandDTO("player_killed")

    @Serializable
    object AllowShooting : GameCommandDTO("allow_shooting")

    @Serializable
    object MakeYourMove : GameCommandDTO("make_your_move")

    @Serializable
    data class RevealPlayerSide(
        val player : PlayerDTO,
        val is_villager : Boolean,
        val is_werewolf : Boolean,
    ) : GameCommandDTO("reveal_player_side")

    @Serializable
    data class GameOver(
        val winner_side : SideDTO,
        val all_players_roles : List<RevealPlayerRole>,
    ): GameCommandDTO("game_over")
}

@Serializable
data class CommandWrapperDTO<T : GameCommandDTO>(
    val type: String,
    val command: T,
) {
    constructor(command: T) : this("game_command_" + command.raw_type, command)
}