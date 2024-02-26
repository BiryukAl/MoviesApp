package ru.kpfu.itis.feature.details.impl.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kpfu.itis.feature.details.api.DetailsFilmRepository
import ru.kpfu.itis.feature.details.api.FilmDetail
import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository

internal class DetailsFilmRepositoryImpl(
    private val remote: RemoteDetailsDataSouse,
    private val local: FavoriteFilmRepository,
    private val mapper: Mapper,
) : DetailsFilmRepository {
    override suspend fun getById(id: Int): Flow<FilmDetail> = flow {

        val remoteFilm = remote.getFilmById(id)
            .map(mapper::responseToModel)
            .map { it.getOrThrow() }

        if (remoteFilm.isSuccess) {
            emit(remoteFilm.getOrThrow())
        } else {
            emit(
                local.getById(id).map {
                    mapper.favoriteToDetails(it).getOrThrow()
                }
                    .getOrThrow()
            )
        }
    }
}
