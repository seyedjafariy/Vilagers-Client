package com.kualagames.shared.storages

import com.kualagames.shared.model.Profile
import com.kualagames.shared.model.UserCredentials
import com.kualagames.shared.network.jsonSerializer
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class UserInfoRepository(
    private val settingStorage: SettingsStorage,
) {
    private val lock = Mutex()

    private var internalUser: UserCredentials? = null
    private var internalProfile: Profile? = null

    fun getCredentialsOrNull(): UserCredentials? {
        return internalUser
    }

    fun getProfileOrNull(): Profile? {
        return internalProfile
    }

    val user: UserCredentials
        get() = getCredentialsOrNull() ?: error("Oops, there was no user")

    val profile: Profile
        get() = getProfileOrNull() ?: error("Oops, there was no user")

    suspend fun store(credentials: UserCredentials, profile: Profile): Unit = lock.withLock {
        val encodedCredentials = jsonSerializer.encodeToString(credentials)
        val encodedProfile = jsonSerializer.encodeToString(profile)

        settingStorage.put(KEY_USER_CREDENTIALS, encodedCredentials)
        settingStorage.put(KEY_USER_PROFILE, encodedProfile)

        internalUser = credentials
        internalProfile = profile
    }

    suspend fun store(profile: Profile): Unit = lock.withLock {
        val encodedProfile = jsonSerializer.encodeToString(profile)

        settingStorage.put(KEY_USER_PROFILE, encodedProfile)

        internalProfile = profile
    }

    suspend fun store(credentials: UserCredentials): Unit = lock.withLock {
        val encodedCredentials = jsonSerializer.encodeToString(credentials)

        settingStorage.put(KEY_USER_CREDENTIALS, encodedCredentials)

        internalUser = credentials
    }

    suspend fun loadFromStorage(): Unit = lock.withLock {
        if (credentialsLoaded) {
            //someone else loaded the data, we don't need to again
            return@withLock
        }

        val userCredentials: UserCredentials? =
            settingStorage
                .get(KEY_USER_CREDENTIALS)
                ?.let { jsonSerializer.decodeFromString(it) }

        val profile: Profile? = if (userCredentials == null) {
            settingStorage.put(KEY_USER_PROFILE, null)
            null
        } else {
            settingStorage
                .get(KEY_USER_PROFILE)
                ?.let { jsonSerializer.decodeFromString(it) }
        }

        internalUser = userCredentials
        internalProfile = profile
    }

    suspend fun credentialExists() : Boolean =
        settingStorage.get(KEY_USER_CREDENTIALS) != null
}

val UserInfoRepository.credentialsLoaded: Boolean
    get() = getCredentialsOrNull() != null

private const val KEY_USER_CREDENTIALS = "KEY_USER_CREDENTIALS"
private const val KEY_USER_PROFILE = "KEY_USER_PROFILE"