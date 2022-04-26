package com.kualagames.shared.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    @SerialName("id")
    val id: String,
    @SerialName("username")
    val username: String,
    @SerialName("bio")
    val bio: String,
    @SerialName("email")
    val email: String,
    @SerialName("emailVerified")
    val emailVerified: Boolean,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("joinDate")
    val joinedDate: LocalDateTime,
    @SerialName("noVillagerGames")
    val noVillagerGames: Int,
    @SerialName("noVillagerWins")
    val noVillagerWins: Int,
    @SerialName("noWerewolfGames")
    val noWerewolfGames: Int,
    @SerialName("noWerewolfWins")
    val noWerewolfWins: Int,
    @SerialName("profileImage")
    val profileImage: String,
)