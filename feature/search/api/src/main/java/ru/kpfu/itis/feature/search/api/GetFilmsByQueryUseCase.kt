package ru.kpfu.itis.feature.search.api

interface GetFilmsByQueryUseCase {
    suspend fun getfilmsByQuery(query: String): Result<List<Film>>
}