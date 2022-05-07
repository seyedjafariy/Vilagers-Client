package com.kualagames.shared.components.rooms

import com.kualagames.shared.network.executeRequest
import io.ktor.client.*
import io.ktor.http.*

class RoomsAPI(
    private val client: HttpClient,
) {

    suspend fun fetchWaitingRooms() = client.executeRequest<Unit> {
        method = HttpMethod.Get
        url {
            encodedPath = "api/game/rooms/all"
        }
    }

}