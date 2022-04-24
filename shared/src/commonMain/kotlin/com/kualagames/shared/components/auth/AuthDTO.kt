package com.kualagames.shared.components.auth

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class AuthDTO(
    @SerialName("expiry")
    val expiry: String,
    @SerialName("token")
    val token: String,
    @SerialName("user")
    val user: UserDTO
)