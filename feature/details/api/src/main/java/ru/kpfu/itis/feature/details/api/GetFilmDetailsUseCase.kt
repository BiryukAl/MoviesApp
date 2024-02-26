package ru.kpfu.itis.feature.details.api

import kotlinx.coroutines.flow.Flow

interface GetFilmDetailsUseCase {
    suspend operator fun invoke(id: Int): Flow<FilmDetail>
}
