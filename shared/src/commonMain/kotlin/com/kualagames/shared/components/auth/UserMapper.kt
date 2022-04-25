package com.kualagames.shared.components.auth

import com.kualagames.shared.model.Profile
import com.kualagames.shared.model.UserCredentials

fun AuthDTO.toCredentials() : UserCredentials = UserCredentials(
    id = user.id,
    username = user.userName,
    token = token,
    expiry = expiry,
)

fun AuthDTO.toProfile() : Profile = with(user) {
    Profile(
        id = id,
        username = userName,
        bio = bio ?: "",
        email = email,
        emailVerified = emailVerified,
        firstName = firstName ?: "",
        joinedDate = joinedDate,
        lastName = lastName ?: "",
        noVillagerGames = noCityGames,
        noVillagerWins = noCityWins,
        noWerewolfGames = noMafiaGames,
        noWerewolfWins = noMafiaWins,
        profileImage = profileImage ?: ""
    )
}