package ru.kpfu.itis.feature.details.impl.data

import ru.kpfu.itis.feature.details.api.DetailsFilmRepository
import ru.kpfu.itis.feature.details.api.FilmDetail
import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository

internal class DetailsFilmRepositoryImpl(
    private val remote: RemoteDetailsDataSouse,
    private val local: FavoriteFilmRepository,
    private val mapper: Mapper,
) : DetailsFilmRepository {
    override suspend fun getById(id: Int): Result<FilmDetail> {
        val remoteFilm = remote.getFilmById(id).flatMap(mapper::responseToModel)

        if (remoteFilm.isFailure) {
            val localFilm = local.getById(id).flatMap(mapper::favoriteToDetails)
            if (localFilm.isFailure) return Result.failure(Throwable())
            return localFilm
        } else {
            return remoteFilm
        }
    }
}
