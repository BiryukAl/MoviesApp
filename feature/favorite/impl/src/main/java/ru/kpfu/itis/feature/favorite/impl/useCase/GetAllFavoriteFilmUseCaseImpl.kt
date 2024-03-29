package ru.kpfu.itis.feature.favorite.impl.useCase

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.feature.favorite.api.FavoriteFilm
import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository
import ru.kpfu.itis.feature.favorite.api.useCase.GetAllFavoriteFilmUseCase

internal class GetAllFavoriteFilmUseCaseImpl(
    private val repository: FavoriteFilmRepository
) : GetAllFavoriteFilmUseCase {
    override operator fun invoke(): Flow<Result<List<FavoriteFilm>>> = repository.getAllFavorite()
}
