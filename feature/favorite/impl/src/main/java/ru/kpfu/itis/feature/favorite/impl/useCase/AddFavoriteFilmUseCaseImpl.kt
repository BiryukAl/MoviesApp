package ru.kpfu.itis.feature.favorite.impl.useCase

import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository
import ru.kpfu.itis.feature.favorite.api.useCase.AddFavoriteFilmUseCase

internal class AddFavoriteFilmUseCaseImpl(
    private val repository: FavoriteFilmRepository
) : AddFavoriteFilmUseCase {
    override suspend operator fun invoke(id: Int) = repository.addFilm(id)
}
