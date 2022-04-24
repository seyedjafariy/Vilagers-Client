package com.kualagames.shared.network;

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.util.*

class APIKeyInterceptor {

    private fun processRequest(context: HttpRequestBuilder) {
        context.headers.append("API_KEY", API_KEY)
    }

    class Config {
    }

    companion object : HttpClientFeature<Config, APIKeyInterceptor> {

        override val key: AttributeKey<APIKeyInterceptor> = AttributeKey("APIKeyInterceptor")

        override fun prepare(block: Config.() -> Unit): APIKeyInterceptor {
            return APIKeyInterceptor()
        }

        override fun install(feature: APIKeyInterceptor, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
                feature.processRequest(context)
                proceed()
            }
        }
    }
}

private const val API_KEY = "random"
