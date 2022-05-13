package com.kualagames.shared.components.waiting_room

import com.kualagames.shared.components.rooms.RoomsAPI
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

class RoomManager(
    private val api: RoomsAPI
) {

    private var _session: DefaultWebSocketSession? = null
    private val session: DefaultWebSocketSession
        get() = _session ?: error("connect first")

    fun createNewAndObserve(roomName: String, gameModeId: String): Flow<String> {
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

    fun joinAndObserve(roomName: String): Flow<String> {
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

    suspend fun send(text: String) {
        session.send(Frame.Text(text))
    }

    private suspend fun handleRoomSocket(emitter: ProducerScope<String>, socket: DefaultWebSocketSession) {
        socket
            .incoming
            .consumeAsFlow()
            .onEach {
                if (it is Frame.Text) {
                    emitter.send(it.readText())
                }
            }
            .launchIn(socket)

        emitter.awaitClose {
            socket.cancel(CancellationException("flow has been canceled"))
            _session = null
        }
    }

}