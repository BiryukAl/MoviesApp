package ru.kpfu.itis.feature.favorite.impl.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


interface RemoteFilmDataSource{
    suspend fun getFilmById(kinopoiskId: Int): Result<FilmResponse>

    class Base(
        private val client: HttpClient,
    ):RemoteFilmDataSource {
        override suspend fun getFilmById(kinopoiskId: Int): Result<FilmResponse> =
            client.get("films/$kinopoiskId").body()
    }

    class Test(): RemoteFilmDataSource {
        override suspend fun getFilmById(kinopoiskId: Int): Result<FilmResponse> {
            TODO("Not yet implemented")
        }
    }
}
