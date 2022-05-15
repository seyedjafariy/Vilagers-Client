package com.kualagames.shared.components.game

import com.kualagames.shared.components.game.model.CommandWrapperDTO
import com.kualagames.shared.components.game.model.GameActionDTO
import com.kualagames.shared.components.game.model.GameCommandDTO
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GameManager(
    private val api: GameAPI,
    private val json: Json,
) {

    private var _session: DefaultWebSocketSession? = null
    private val session: DefaultWebSocketSession
        get() = _session ?: error("connect first")

    fun joinAndObserve(roomName: String): Flow<CommandWrapperDTO<GameCommandDTO>> {
        if (_session != null) {
            error("another session is already running")
        }
        return callbackFlow {
            api.joinRoom(roomName) {
                _session = this
                handleConnection(this@callbackFlow, this)
            }
        }
    }

    private suspend fun handleConnection(
        emitter: ProducerScope<CommandWrapperDTO<GameCommandDTO>>,
        socket: DefaultWebSocketSession
    ) {
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

    suspend fun send(action: GameActionDTO) {
        session.send(Frame.Text(json.encodeToString(action)))
    }
}