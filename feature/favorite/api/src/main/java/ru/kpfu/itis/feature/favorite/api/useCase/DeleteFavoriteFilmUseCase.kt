package ru.kpfu.itis.feature.favorite.api.useCase

interface DeleteFavoriteFilmUseCase {
    suspend operator fun invoke(id: Int): Result<Unit>
}