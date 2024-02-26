package ru.kpfu.itis.feature.details.api

import kotlinx.coroutines.flow.Flow

interface DetailsFilmRepository {
    suspend fun getById(id: Int): Flow<FilmDetail>
}