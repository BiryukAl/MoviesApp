package ru.kpfu.itis.feature.search.api

interface SearchFilmRepository {
    suspend fun getFilmByQuery(query: String): Result<List<Film>>
}