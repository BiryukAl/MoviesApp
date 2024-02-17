package ru.kpfu.itis.feature.favorite.impl.useCase

import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository
import ru.kpfu.itis.feature.favorite.api.useCase.AddFavoriteFilm

class AddFavoriteFilmImpl(
    private val repository: FavoriteFilmRepository
) : AddFavoriteFilm {
    override suspend operator fun invoke(id: Int): Result<Unit> = repository.addFilm(id)
}
