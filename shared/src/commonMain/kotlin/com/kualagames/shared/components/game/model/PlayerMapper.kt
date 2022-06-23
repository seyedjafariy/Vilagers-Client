package com.kualagames.shared.components.game.model

fun PlayerDTO.toPlayer(self: Boolean) = with(user) {
    Player(
        id,
        userName,
        profileImage,
        self,
        hasLost,
        online,
        null,
    )
}