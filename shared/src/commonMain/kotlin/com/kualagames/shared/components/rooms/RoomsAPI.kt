package com.kualagames.shared.components.rooms

import com.kualagames.shared.model.dto.GameModeDTO
import com.kualagames.shared.model.dto.ListWrapperDTO
import com.kualagames.shared.network.*
import com.kualagames.shared.storages.UserInfoRepository
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.utils.io.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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

    fun createRoom(roomName: String, gameModeId: String) = callbackFlow<String> {
        client.webSocket(urlString = "wss://vilagers.com/api/game/rooms/new/$roomName",{
            headers.append("gameMode", gameModeId)

        }){
            handleRoomSocket(this@callbackFlow, this)
        }
    }

    private suspend fun handleRoomSocket(emitter: ProducerScope<String>, socket: DefaultWebSocketSession) {
        socket
            .incoming
            .consumeAsFlow()
            .onEach {
                if(it is Frame.Text){
                    emitter.send(it.readText())
                }
            }
            .launchIn(socket)

        emitter.awaitClose {
            socket.cancel(CancellationException("flow has been canceled"))
        }
    }
}