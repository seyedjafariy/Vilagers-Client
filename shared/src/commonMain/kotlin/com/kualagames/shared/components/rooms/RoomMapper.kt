package com.kualagames.shared.components.rooms

fun RoomDTO.toDomain() = Room(roomName, users.size)