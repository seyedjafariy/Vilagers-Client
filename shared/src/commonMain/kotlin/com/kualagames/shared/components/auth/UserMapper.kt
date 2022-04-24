package com.kualagames.shared.components.auth

import com.kualagames.shared.model.Profile
import com.kualagames.shared.model.UserCredentials

fun AuthDTO.toCredentials() : UserCredentials = UserCredentials(
    expiry = expiry,
    token = token,
    username = user.userName,
    id = user.id
)

fun AuthDTO.toProfile() : Profile = with(user) {
    Profile(
        id = id,
        userName = userName,
        bio = bio ?: "",
        email = email,
        emailVerified = emailVerified,
        firstName = firstName ?: "",
        joinedDate = joinedDate,
        lastName = lastName ?: "",
        noCityGames = noCityGames,
        noCityWins = noCityWins,
        noMafiaGames = noMafiaGames,
        noMafiaWins = noMafiaWins,
        profileImage = profileImage ?: ""
    )
}