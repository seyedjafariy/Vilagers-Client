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