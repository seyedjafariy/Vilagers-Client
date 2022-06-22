package com.kualagames.shared.components.rooms

import com.kualagames.shared.model.dto.GameModeDTO
import com.kualagames.shared.model.dto.ListWrapperDTO
import com.kualagames.shared.network.WEBSOCKET_SCHEME
import com.kualagames.shared.network.defaultRequest
import com.kualagames.shared.network.executeRequest
import com.kualagames.shared.network.mapToList
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*

class RoomsAPI(
    private val client: HttpClient,
) {

    suspend fun fetchWaitingRooms() = client.executeRequest<ListWrapperDTO<RoomDTO>> {
        defaultRequest()
        method = HttpMethod.Get
        url {
            encodedPath = "api/game/rooms/all"
        }
    }.mapToList("rooms")

    suspend fun fetchGameModes() = client.executeRequest<ListWrapperDTO<GameModeDTO>> {
        defaultRequest()
        method = HttpMethod.Get
        url {
            encodedPath = "api/game/modes"
        }
    }.mapToList("modes")

    suspend fun createRoom(roomName: String, gameModeId: String, session: suspend DefaultWebSocketSession.() -> Unit) {
        client.webSocket(urlString = "$WEBSOCKET_SCHEME/api/game/rooms/new/$roomName", {
            headers.append("gameMode", gameModeId)

        }) {
            session()
        }
    }

    suspend fun joinRoom(roomName: String, session: suspend DefaultWebSocketSession.() -> Unit) {
        client.webSocket(urlString = "$WEBSOCKET_SCHEME/api/game/rooms/join/$roomName", {}) {
            session()
        }
    }
}