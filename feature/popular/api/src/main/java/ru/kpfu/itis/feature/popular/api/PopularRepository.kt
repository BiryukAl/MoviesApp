package ru.kpfu.itis.feature.popular.api

interface PopularRepository {
    suspend fun getPopularFilms(page: Int): Result<List<Film>>
}
