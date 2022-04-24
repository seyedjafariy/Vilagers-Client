package com.kualagames.shared.model

data class UserCredentials(
    val expiry: String,
    val token: String,
    val username: String,
    val id : String,
)