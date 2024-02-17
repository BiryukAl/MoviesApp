package ru.kpfu.itis.feature.favorite.impl.useCase

import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository
import ru.kpfu.itis.feature.favorite.api.useCase.DeleteFavoriteFilm

class DeleteFavoriteFilmImpl(
    private val repository: FavoriteFilmRepository
) : DeleteFavoriteFilm {
    override suspend operator fun invoke(id: Int): Result<Unit> = repository.delete(id)
}
