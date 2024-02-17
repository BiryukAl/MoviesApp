package ru.kpfu.itis.feature.details.api

interface DetailsFilmRepository {
    suspend fun getById(id: Int): Result<FilmDetail>
}