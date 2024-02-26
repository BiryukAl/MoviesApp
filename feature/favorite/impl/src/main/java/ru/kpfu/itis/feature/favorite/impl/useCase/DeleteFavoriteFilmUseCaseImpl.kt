package ru.kpfu.itis.feature.favorite.impl.useCase

import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository
import ru.kpfu.itis.feature.favorite.api.useCase.DeleteFavoriteFilmUseCase

internal class DeleteFavoriteFilmUseCaseImpl(
    private val repository: FavoriteFilmRepository
) : DeleteFavoriteFilmUseCase {
    override suspend operator fun invoke(id: Int): Result<Unit> = repository.delete(id)
}
