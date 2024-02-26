package ru.kpfu.itis.feature.popular.api

import kotlinx.coroutines.flow.Flow

interface GetPopularFilmsUseCase {
    suspend operator fun invoke(page: Int = 1): Flow<List<Film>>
}
