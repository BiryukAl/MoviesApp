package ru.kpfu.itis.feature.favorite.impl.useCase

import ru.kpfu.itis.feature.favorite.api.FavoriteFilm
import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository
import ru.kpfu.itis.feature.favorite.api.useCase.GetFavoriteFilmDetails

internal class GetFavoriteFilmDetailsImpl(
    private val repository: FavoriteFilmRepository
) : GetFavoriteFilmDetails {
    override operator fun invoke(id: Int): Result<FavoriteFilm> = repository.getById(id)
}
