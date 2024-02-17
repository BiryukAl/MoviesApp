package ru.kpfu.itis.feature.details.impl.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


interface RemoteDetailsDataSouse {
    suspend fun getFilmById(kinopoiskId: Int): Result<FilmResponse>

    class Base(
        private val client: HttpClient
    ): RemoteDetailsDataSouse {
        override suspend fun getFilmById(kinopoiskId: Int): Result<FilmResponse> =
            client.get("films/$kinopoiskId").body()
    }

    class Test(): RemoteDetailsDataSouse {
        override suspend fun getFilmById(kinopoiskId: Int): Result<FilmResponse> {
            TODO("Not yet implemented")
        }
    }

}
