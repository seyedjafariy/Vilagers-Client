package com.kualagames.shared.components.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    @SerialName("id")
    val id: String,
    @SerialName("userName")
    val userName: String,
    @SerialName("bio")
    val bio: String? = "",
    @SerialName("email")
    val email: String = "",
    @SerialName("emailVerified")
    val emailVerified: Boolean = false,
    @SerialName("firstName")
    val firstName: String? = "",
    @SerialName("lastName")
    val lastName: String? = "",
    @SerialName("joinedDate")
    val joinedDate: String,
    @SerialName("noCityGames")
    val noCityGames: Int,
    @SerialName("noCityWins")
    val noCityWins: Int,
    @SerialName("noMafiaGames")
    val noMafiaGames: Int,
    @SerialName("noMafiaWins")
    val noMafiaWins: Int,
    @SerialName("profileImage")
    val profileImage: String? = "",
)