package ru.kpfu.itis.feature.search.api

import kotlinx.coroutines.flow.Flow


interface SearchFilmRepository {
    suspend fun getFilmByQuery(query: String): Flow<List<Film>>
}