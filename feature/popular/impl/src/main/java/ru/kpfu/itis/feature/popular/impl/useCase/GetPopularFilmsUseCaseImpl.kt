package ru.kpfu.itis.feature.popular.impl.useCase

import ru.kpfu.itis.feature.popular.api.Film
import ru.kpfu.itis.feature.popular.api.GetPopularFilmsUseCase
import ru.kpfu.itis.feature.popular.api.PopularRepository

internal class GetPopularFilmsUseCaseImpl(
    private val repository: PopularRepository
) : GetPopularFilmsUseCase {
    override suspend fun invoke(page: Int): Result<List<Film>> =
        repository.getPopularFilms(page)

}
