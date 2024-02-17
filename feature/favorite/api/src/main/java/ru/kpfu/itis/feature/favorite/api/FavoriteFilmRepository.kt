package ru.kpfu.itis.feature.favorite.api

import kotlinx.coroutines.flow.Flow

interface FavoriteFilmRepository {
    fun getAllFavorite(): Flow<Result<List<FavoriteFilm>>>

    fun getById(id: Int): Result<FavoriteFilm>
    suspend fun addFilm(id: Int): Result<Unit>
    suspend fun delete(id: Int): Result<Unit>
}
