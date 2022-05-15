package com.kualagames.shared.components.game

import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*

class GameAPI(
    private val client : HttpClient
) {

    suspend fun joinRoom(gameId: String, session: suspend DefaultWebSocketSession.() -> Unit) {
        client.webSocket(urlString = "wss://vilagers.com/api/game/play/$gameId", {}) {
            session()
        }
    }

}