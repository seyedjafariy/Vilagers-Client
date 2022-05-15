package com.kualagames.shared.components.game.model

import com.kualagames.shared.components.auth.UserDTO
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDTO(
    val user: UserDTO,
    val online: Boolean,
    val hasLost: Boolean,
)