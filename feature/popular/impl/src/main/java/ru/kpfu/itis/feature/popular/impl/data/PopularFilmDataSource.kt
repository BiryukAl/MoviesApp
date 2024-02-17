package ru.kpfu.itis.feature.popular.impl.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.kpfu.itis.feature.popular.impl.data.response.FilmsResponse


interface PopularFilmDataSource{
    suspend fun getPopularFilms(page: Int): Result<FilmsResponse>

    class Base(
        private val client: HttpClient
    ): PopularFilmDataSource {

        override suspend fun getPopularFilms(page: Int): Result<FilmsResponse> =
            client.get("films/collections") {
                url {
                    parameters.append("type", "TOP_POPULAR_MOVIES")
                    parameters.append("page", page.toString())
                }
            }.body()

    }

    class Test() : PopularFilmDataSource {
        override suspend fun getPopularFilms(page: Int): Result<FilmsResponse> {
            TODO("Not yet implemented")
        }
    }
}
