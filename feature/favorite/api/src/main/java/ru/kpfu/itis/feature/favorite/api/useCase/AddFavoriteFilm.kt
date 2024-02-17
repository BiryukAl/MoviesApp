package ru.kpfu.itis.feature.favorite.api.useCase

interface AddFavoriteFilm {
    suspend operator fun invoke(id: Int): Result<Unit>
}