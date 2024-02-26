package ru.kpfu.itis.feature.favorite.api.useCase

import ru.kpfu.itis.feature.favorite.api.FavoriteFilm

interface GetFavoriteFilmDetailsUseCase {
    operator fun invoke(id: Int): Result<FavoriteFilm>
}