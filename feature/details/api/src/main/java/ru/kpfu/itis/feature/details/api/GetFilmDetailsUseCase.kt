package ru.kpfu.itis.feature.details.api

interface GetFilmDetailsUseCase {
    suspend operator fun invoke(id: Int): Result<FilmDetail>
}
