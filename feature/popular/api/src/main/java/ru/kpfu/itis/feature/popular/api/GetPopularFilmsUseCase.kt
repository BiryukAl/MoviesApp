package ru.kpfu.itis.feature.popular.api

interface GetPopularFilmsUseCase {
    suspend operator fun invoke(page: Int = 1): Result<List<Film>>
}
