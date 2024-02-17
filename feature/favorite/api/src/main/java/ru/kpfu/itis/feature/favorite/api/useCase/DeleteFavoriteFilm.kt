package ru.kpfu.itis.feature.favorite.api.useCase

interface DeleteFavoriteFilm {
    suspend operator fun invoke(id: Int): Result<Unit>
}