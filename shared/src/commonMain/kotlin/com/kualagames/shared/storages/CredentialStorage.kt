package com.kualagames.shared.storages

import com.kualagames.shared.model.UserCredentials

class CredentialStorage {

    fun getUserOrNull(): UserCredentials? {
        return null //TODO "Not yet implemented"
    }

    val user: UserCredentials
        get() = getUserOrNull() ?: error("Oops, there was no user")

    suspend fun store(credits: UserCredentials) {

    }
}

val CredentialStorage.userExists: Boolean
    get() = getUserOrNull() != null
