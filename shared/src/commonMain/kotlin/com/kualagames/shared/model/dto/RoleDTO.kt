package com.kualagames.shared.model.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class RoleDTO(
    @SerialName("roleDesc")
    val roleDesc: String,
    @SerialName("roleName")
    val roleName: String,
    @SerialName("side")
    val side: String,
    @SerialName("type")
    val type: String
)