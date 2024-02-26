package ru.kpfu.itis.feature.search.api

import kotlinx.coroutines.flow.Flow

interface GetFilmsByQueryUseCase {
    suspend operator fun invoke(query: String): Flow<List<Film>?>

}
