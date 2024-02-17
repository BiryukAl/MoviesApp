package ru.kpfu.itis.feature.favorite.api.useCase

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.feature.favorite.api.FavoriteFilm

interface GetAllFavoriteFilm {
    operator fun invoke(): Flow<Result<List<FavoriteFilm>>>
}