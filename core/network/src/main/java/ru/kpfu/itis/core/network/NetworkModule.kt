package ru.kpfu.itis.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {
        provideKTorHttpClient()
    }
}

const val KEY_NAME_HEADER = "x-api-key"
fun provideKTorHttpClient() =
    HttpClient(OkHttp) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            url(BuildConfig.BASE_URL)
            header(KEY_NAME_HEADER, BuildConfig.API_KEY)
        }
    }


