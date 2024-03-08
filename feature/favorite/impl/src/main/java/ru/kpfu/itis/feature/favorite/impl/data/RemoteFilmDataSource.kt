package ru.kpfu.itis.feature.favorite.impl.data

import io.ktor.client.HttpClient
import ru.kpfu.itis.core.network.getResult


internal interface RemoteFilmDataSource {
    suspend fun getFilmById(kinopoiskId: Int): Result<FilmResponse>

    class Base(
        private val client: HttpClient,
    ) : RemoteFilmDataSource {
        override suspend fun getFilmById(kinopoiskId: Int): Result<FilmResponse> =
            client.getResult("films/$kinopoiskId")
    }

}
