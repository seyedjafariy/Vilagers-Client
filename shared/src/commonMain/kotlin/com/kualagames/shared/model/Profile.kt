package com.kualagames.shared.model

data class Profile(
    val id: String,
    val userName: String,
    val bio: String,
    val email: String,
    val emailVerified: Boolean,
    val firstName: String,
    val joinedDate: String,
    val lastName: String,
    val noCityGames: Int,
    val noCityWins: Int,
    val noMafiaGames: Int,
    val noMafiaWins: Int,
    val profileImage: String,
)