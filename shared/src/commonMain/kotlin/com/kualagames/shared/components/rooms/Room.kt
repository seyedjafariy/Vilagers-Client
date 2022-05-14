package com.kualagames.shared.components.rooms

data class Room(
    val name : String,
    val playerCount : Int,
){
    enum class Status(val rawName: String) {
        Waiting("Waiting"),
        Filled("Filled"),
        Started("Started"),
        ;

        companion object {
            operator fun invoke(raw: String) =
                values().first { it.rawName == raw }
        }
    }
}