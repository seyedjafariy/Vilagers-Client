package com.kualagames.shared.components.auth

import com.kualagames.shared.network.Response
import com.kualagames.shared.network.addFormData
import com.kualagames.shared.network.executeRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthAPI(
    private val client: HttpClient
) {
    suspend fun echo(): Response<Map<String, List<String>>> = client.executeRequest {
        method = HttpMethod.Get
        url {
            encodedPath = "api/echo"
            parameter("page", 123)
            parameter("query", "hello")
            parameter("include_adult", false)
        }
    }

    suspend fun login(username : String, password : String) : Response<AuthDTO> = client.executeRequest {
        method = HttpMethod.Post
        url {
            encodedPath = "api/auth/login"
            addFormData(
                "user_name" to username,
                "password" to password,
            )
        }
    }
}