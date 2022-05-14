package com.kualagames.shared.components.waiting_room

import com.kualagames.shared.components.rooms.RoomDTO
import com.kualagames.shared.components.rooms.RoomsAPI
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RoomManager(
    private val api: RoomsAPI,
    private val json: Json,
) {

    private var _session: DefaultWebSocketSession? = null
    private val session: DefaultWebSocketSession
        get() = _session ?: error("connect first")

    fun createNewAndObserve(roomName: String, gameModeId: String): Flow<RoomDTO> {
        if (_session != null) {
            error("another session is already running")
        }

        return callbackFlow {
            api.createRoom(roomName, gameModeId) {
                _session = this
                handleRoomSocket(this@callbackFlow, this)
            }
        }
    }

    fun joinAndObserve(roomName: String): Flow<RoomDTO> {
        if (_session != null) {
            error("another session is already running")
        }
        return callbackFlow {
            api.joinRoom(roomName) {
                _session = this
                handleRoomSocket(this@callbackFlow, this)
            }
        }
    }

    suspend fun send(command: WaitingRoomCommand) {
        session.send(Frame.Text(json.encodeToString(command)))
    }

    private suspend fun handleRoomSocket(emitter: ProducerScope<RoomDTO>, socket: DefaultWebSocketSession) {
        socket
            .incoming
            .consumeAsFlow()
            .onEach {
                if (it is Frame.Text) {
                    val jsonText = it.readText()

                    try {
                        emitter.send(json.decodeFromString(jsonText))
                    } catch (e: Exception) {
                    }
                }
            }
            .launchIn(socket)

        emitter.awaitClose {
            socket.cancel(CancellationException("flow has been canceled"))
            _session = null
        }
    }

}