package com.kualagames.shared.network

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import org.koin.dsl.module

internal val networkModule = module {
    single {
        val engine : HttpClientEngine? = getOrNull()

        val clientConfig : HttpClientConfig<*>.() -> Unit = get()

        if (engine == null) {
            HttpClient(clientConfig)
        } else {
            HttpClient(engine, clientConfig)
        }
    }

    factory<HttpClientConfig<*>.() -> Unit> {
        {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
//    install(Logging) {
//        logger = Logger.DEFAULT
//        level = LogLevel.ALL
//    }

            install(TokenInterceptor) {
                this.userInfoRepository = get()
                this.requestsWithoutAuthorization = setOf("api/auth/login")
            }

            install(APIKeyInterceptor){
            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                }
            }
        }
    }
}

private const val BASE_URL = "vilagers.com"
