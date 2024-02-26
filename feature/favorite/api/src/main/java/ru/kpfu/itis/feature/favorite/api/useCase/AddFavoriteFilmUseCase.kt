package ru.kpfu.itis.feature.favorite.api.useCase

import kotlinx.coroutines.flow.Flow

interface AddFavoriteFilmUseCase {
    suspend operator fun invoke(id: Int): Flow<Unit>
}