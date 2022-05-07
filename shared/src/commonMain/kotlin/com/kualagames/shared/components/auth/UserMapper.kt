package com.kualagames.shared.components.auth

import com.kualagames.shared.model.Profile
import com.kualagames.shared.model.UserCredentials
import com.kualagames.shared.network.jsonSerializer
import com.kualagames.shared.utils.formatAndParseToLocalDateTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.decodeFromString

fun AuthDTO.toCredentials() : UserCredentials = UserCredentials(
    id = user.id,
    username = user.userName,
    token = token,
    expiry = expiry.toInstant(),
)

fun AuthDTO.toProfile() : Profile = with(user) {
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
}