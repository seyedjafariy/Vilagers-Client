@file:Suppress("EXPERIMENTAL_API_USAGE")
@file:OptIn(InternalSerializationApi::class)

package com.kualagames.shared.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.charsets.*
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

suspend inline fun <reified T> HttpClient.executeRequest(
   block: HttpRequestBuilder.() -> Unit
): Response<T> {
    val response = request<HttpResponse>(block)

    val statusCode = response.status.value

    return if (statusCode in 200..299) {
        parseSuccess(statusCode, response)
    } else {
        @Suppress("UNCHECKED_CAST")
        Response.Error(statusCode, response.responseToString()) as Response<T>
    }
}

internal const val BASE_URL = "vilagers.com"

@Suppress("DEPRECATION")
suspend inline fun <reified T> parseSuccess(code: Int, response: HttpResponse): Response<T> {
    val type = typeInfo<T>()
    val serializer = type.kotlinType?.let { serializer(it) } ?: type.type.serializer()

    val text = response.responseToString()

    val parsed = jsonSerializer.decodeFromString(serializer, text)

    return Response.Success(code, parsed as T)
}

suspend fun HttpResponse.responseToString(): String =
    readText(Charsets.UTF_8)