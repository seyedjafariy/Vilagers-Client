package com.kualagames.shared.network;

import com.kualagames.shared.storages.CredentialStorage
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.util.*

class TokenInterceptor(
    private val credentialStorage: CredentialStorage,
    private val requestsWithoutAuthorization: Set<String>
) {

    private suspend fun refreshToken(
        client: HttpClient,
        refreshToken: String
    ): String {
        TODO()
//        val request = HttpRequestBuilder().apply {
//            url(refreshTokenRequestUrl)
//            method = HttpMethod.Post
//            body = RefreshTokenBody(refreshToken)
//            header("Accept", "application/json")
//            header("Content-Type", "application/json")
//           // set up your refresh token request
//        }

        // request will throw in case of unsuccessful response
//        val response = client.request<HttpResponse>(request)
//        val model = // decode your response
        // save token to local storage
//        return model.token
    }

    private suspend fun processRequest(client: HttpClient, context: HttpRequestBuilder) {
        val url = context.url.encodedPath
        val user = credentialStorage.getUserOrNull()
        if (requestsWithoutAuthorization.contains(url) || user == null) {
            return
        } else {
            val accessToken = user.token
//            if (/* need to refresh token first */) {
//                val token = refreshToken(client, authModel.refreshToken)
                // Add your new token to headers
//            } else {
                // Add old token to headers
//            }
            context.headers.append("Authorization", "Bearer $accessToken")
        }
    }

    class Config {
        lateinit var credentialStorage: CredentialStorage
        lateinit var requestsWithoutAuthorization: Set<String>
    }

    companion object : HttpClientFeature<Config, TokenInterceptor> {

        override val key: AttributeKey<TokenInterceptor> = AttributeKey("TokenInterceptor")

        override fun prepare(block: Config.() -> Unit): TokenInterceptor {
            val config = Config().apply(block)
            return TokenInterceptor(
                config.credentialStorage,
                config.requestsWithoutAuthorization
            )
        }

        override fun install(feature: TokenInterceptor, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
                feature.processRequest(scope, context)
                proceed()
            }
        }
    }
}