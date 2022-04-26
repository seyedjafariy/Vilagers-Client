package com.kualagames.shared.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(
    @SerialName("id")
    val id: String,
    @SerialName("username")
    val username: String,
    @SerialName("token")
    val token: String,
    @SerialName("expiry")
    val expiry: LocalDateTime,
)