package ru.kpfu.itis.feature.favorite.impl.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import ru.kpfu.itis.core.network.ConnectionError


internal interface RemoteFilmDataSource {
    suspend fun getFilmById(kinopoiskId: Int): Result<FilmResponse>

    class Base(
        private val client: HttpClient,
    ) : RemoteFilmDataSource {
        override suspend fun getFilmById(kinopoiskId: Int): Result<FilmResponse> {
            val response = client.get("films/$kinopoiskId")
            return if (response.status.isSuccess()) {
                try {
                    Result.success(response.body<FilmResponse>())
                } catch (ex: Exception) {
                    Result.failure(ex)
                }
            } else {
                Result.failure(ConnectionError())
            }
        }
    }

    class Test() : RemoteFilmDataSource {
        override suspend fun getFilmById(kinopoiskId: Int): Result<FilmResponse> {
            TODO("Not yet implemented")
        }
    }
}
