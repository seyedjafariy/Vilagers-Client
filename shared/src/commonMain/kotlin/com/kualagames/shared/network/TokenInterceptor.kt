package com.kualagames.shared.network;

import com.kualagames.shared.storages.UserInfoRepository
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.util.*

class TokenInterceptor(
    private val userInfoRepository: UserInfoRepository,
    private val excludedPaths: Set<String>
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
        val user = userInfoRepository.getCredentialsOrNull()
        if (excludedPaths.contains(url) || user == null) {
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
        lateinit var userInfoRepository: UserInfoRepository
        lateinit var excludedPaths: Set<String>
    }

    companion object : HttpClientFeature<Config, TokenInterceptor> {

        override val key: AttributeKey<TokenInterceptor> = AttributeKey("TokenInterceptor")

        override fun prepare(block: Config.() -> Unit): TokenInterceptor {
            val config = Config().apply(block)
            return TokenInterceptor(
                config.userInfoRepository,
                config.excludedPaths
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