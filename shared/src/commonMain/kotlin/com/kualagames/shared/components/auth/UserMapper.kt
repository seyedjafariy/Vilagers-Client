package com.kualagames.shared.components.auth

import com.kualagames.shared.model.Profile
import com.kualagames.shared.model.UserCredentials
import kotlinx.datetime.toInstant

fun AuthDTO.toCredentials(): UserCredentials = UserCredentials(
    id = user.id,
    username = user.userName,
    token = token,
    expiry = expiry.toInstant(),
)

fun AuthDTO.toProfile(): Profile = user.toProfile()

fun UserDTO.toProfile(): Profile =
    Profile(
        id = id,
        username = userName,
        bio = bio ?: "",
        email = email,
        emailVerified = emailVerified,
        firstName = firstName ?: "",
        joinedDate = joinedDate.toInstant(),
        lastName = lastName ?: "",
        noVillagerGames = noCityGames,
        noVillagerWins = noCityWins,
        noWerewolfGames = noMafiaGames,
        noWerewolfWins = noMafiaWins,
        profileImage = profileImage ?: ""
    )