package com.kualagames.shared.network

import kotlinx.serialization.json.Json

val jsonSerializer = Json {
    ignoreUnknownKeys = true
    isLenient = true
}