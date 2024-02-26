package ru.kpfu.itis.feature.popular.api

import kotlinx.coroutines.flow.Flow

interface PopularRepository {
    suspend fun getPopularFilms(page: Int): Flow<List<Film>>
}
