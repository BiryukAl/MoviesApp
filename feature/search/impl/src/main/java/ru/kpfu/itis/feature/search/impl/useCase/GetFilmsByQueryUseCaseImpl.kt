package ru.kpfu.itis.feature.search.impl.useCase

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.feature.search.api.Film
import ru.kpfu.itis.feature.search.api.GetFilmsByQueryUseCase
import ru.kpfu.itis.feature.search.api.SearchFilmRepository

internal class GetFilmsByQueryUseCaseImpl(
    private val repository: SearchFilmRepository,
) : GetFilmsByQueryUseCase {
    override suspend fun invoke(query: String): Flow<List<Film>> =
        repository.getFilmByQuery(query)
}
