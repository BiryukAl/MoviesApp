package ru.kpfu.itis.feature.popular.impl.data

import io.ktor.client.HttpClient
import ru.kpfu.itis.core.network.getResult


internal interface PopularFilmDataSource {
    suspend fun getPopularFilms(page: Int): Result<FilmsResponse>

    class Base(
        private val client: HttpClient
    ) : PopularFilmDataSource {

        override suspend fun getPopularFilms(page: Int): Result<FilmsResponse> =
            client.getResult("films/collections") {
                url {
                    parameters.append("type", "TOP_POPULAR_MOVIES")
                    parameters.append("page", page.toString())
                }
            }
    }
}
