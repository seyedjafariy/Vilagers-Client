package com.kualagames.shared.settings

import com.kualagames.vilagers.database.SettingsQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SettingsStorage(
    private val queries: SettingsQueries
) {

    suspend fun put(key: String, value: String?, context: CoroutineContext = Dispatchers.Default) {
        withContext(context) {
            queries.upsert(key, value)
        }
    }

    suspend fun get(key: String, context: CoroutineContext = Dispatchers.Default): String? =
        withContext(context) {
            delay(3000)
            queries.getWithKey(key).executeAsOneOrNull()?.value_
        }

    fun observeKey(key: String, context: CoroutineContext): Flow<String?> =
        queries.getWithKey(key)
            .asFlow()
            .mapToOneOrNull(context)
            .map {
                it?.value_
            }

    fun observeKey(
        key: String,
        defaultValue: suspend () -> String,
        context: CoroutineContext = Dispatchers.Default
    ): Flow<String> =
        observeKey(key, context)
            .distinctUntilChanged()
            .map {
                it ?: defaultValue()
            }

}