package ru.kpfu.itis.feature.popular.impl.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import ru.kpfu.itis.core.network.ConnectionError


internal interface PopularFilmDataSource {
    suspend fun getPopularFilms(page: Int): Result<FilmsResponse>

    class Base(
        private val client: HttpClient
    ) : PopularFilmDataSource {

        override suspend fun getPopularFilms(page: Int): Result<FilmsResponse> {

            val response = client.get("films/collections") {
                url {
                    parameters.append("type", "TOP_POPULAR_MOVIES")
                    parameters.append("page", page.toString())
                }
            }

            return if (response.status.isSuccess()) {
                try {
                    Result.success(response.body<FilmsResponse>())
                } catch (ex: Exception) {
                    Result.failure(ConnectionError())
                }
            } else {
                Result.failure(ConnectionError())
            }
        }
    }

    class Test() : PopularFilmDataSource {
        override suspend fun getPopularFilms(page: Int): Result<FilmsResponse> {
            TODO("Not yet implemented")
        }
    }
}
