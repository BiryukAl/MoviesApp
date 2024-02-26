package ru.kpfu.itis.feature.favorite.impl.useCase

import ru.kpfu.itis.feature.favorite.api.FavoriteFilm
import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository
import ru.kpfu.itis.feature.favorite.api.useCase.GetFavoriteFilmDetailsUseCase

internal class GetFavoriteFilmDetailsUseCaseImpl(
    private val repository: FavoriteFilmRepository
) : GetFavoriteFilmDetailsUseCase {
    override operator fun invoke(id: Int): Result<FavoriteFilm> = repository.getById(id)
}
