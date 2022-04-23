package com.kualagames.shared.network

@Suppress("MemberVisibilityCanBePrivate")
sealed class Response<T>(
    val statusCode: Int
) {
    data class Success<K>(
        val status: Int,
        val data: K
    ) : Response<K>(status)

    data class Error(
        val status: Int,
        val body: String,
        val throwable: Throwable? = null
    ) : Response<Nothing>(status)

    val isSuccessful: Boolean
        get() = statusCode in 200..299

    val isNotSuccessful: Boolean
        get() = !isSuccessful
}

fun <T> Response<T>.orNull(whenNull: (Response.Error) -> T): T {
    return if (isSuccessful) {
        (this as Response.Success<T>).data
    } else {
        whenNull((this as Response.Error))
    }
}

fun <T> Response<T>.successData() : T {
    val success = this as? Response.Success<T>

    return if (success != null) {
        success.data
    }else{
        error("failed response= $this")
    }
}

