package ru.kpfu.itis.feature.details.impl.useCase

import ru.kpfu.itis.feature.details.api.DetailsFilmRepository
import ru.kpfu.itis.feature.details.api.FilmDetail
import ru.kpfu.itis.feature.details.api.GetFilmDetailsUseCase

class GetDetailFilmUseCaseImpl(
    private val repository: DetailsFilmRepository,
) : GetFilmDetailsUseCase {
    override suspend operator fun invoke(id: Int): Result<FilmDetail> =
        repository.getById(id)

}
