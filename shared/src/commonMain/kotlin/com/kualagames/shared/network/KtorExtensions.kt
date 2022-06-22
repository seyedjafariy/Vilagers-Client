package com.kualagames.shared.network

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

fun HttpRequestBuilder.addJsonBody(body: Any) {
    contentType(ContentType.Application.Json)
    this.body = body
}

fun HttpRequestBuilder.addFormData(vararg params: Pair<String, String>) {
    body = FormDataContent(Parameters.build {
        params.forEach {
            append(it.first, it.second)
        }
    })
}

fun HttpRequestBuilder.defaultRequest() {
    debugHost()
}

fun HttpRequestBuilder.debugHost(){
    url {
        host = DEBUG_BASE_URL
    }
}

fun HttpRequestBuilder.prodHost(){
    url {
        protocol = URLProtocol.HTTPS
        host = BASE_URL
    }
}

private const val BASE_URL = "vilagers.com"
private const val DEBUG_BASE_URL = "192.168.1.105:8080"

const val WEBSOCKET_SCHEME_PROD = "wss://$BASE_URL"
const val WEBSOCKET_SCHEME_DEBUG = "ws://$DEBUG_BASE_URL"
const val WEBSOCKET_SCHEME = WEBSOCKET_SCHEME_DEBUG
