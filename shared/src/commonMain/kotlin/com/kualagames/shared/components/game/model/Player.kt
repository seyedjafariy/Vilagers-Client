package com.kualagames.shared.components.game.model

import com.kualagames.shared.model.Role

data class Player(
    val id : String,
    val userName : String,
    val avatar : String?,
    val self : Boolean,
    val lost : Boolean,
    val online : Boolean,
    val role : Role?,
)