package ru.kpfu.itis.feature.details.impl.data

import io.ktor.client.HttpClient
import ru.kpfu.itis.core.network.getResult


internal interface RemoteDetailsDataSouse {
    suspend fun getFilmById(kinopoiskId: Int): Result<FilmResponse>

    class Base(
        private val client: HttpClient
    ) : RemoteDetailsDataSouse {
        override suspend fun getFilmById(kinopoiskId: Int): Result<FilmResponse> =
            client.getResult("films/$kinopoiskId")
    }

}
