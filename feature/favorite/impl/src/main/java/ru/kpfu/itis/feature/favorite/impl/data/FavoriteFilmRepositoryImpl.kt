package ru.kpfu.itis.feature.favorite.impl.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.feature.favorite.api.FavoriteFilm
import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository

internal class FavoriteFilmRepositoryImpl(
    private val local: LocalFavoriteDataSource,
    private val remote: RemoteFilmDataSource,
    private val mappers: Mappers,

    ) : FavoriteFilmRepository {
    override fun getAllFavorite(): Flow<Result<List<FavoriteFilm>>> {
        return local.getAllFavorite().map {
            it.map { list ->
                list.mapNotNull { film ->
                    mappers.entityToModel(film).getOrNull()
                }
            }
        }
    }

    override fun getById(id: Int): Result<FavoriteFilm> =
        local.getById(id).flatMap(mappers::entityToModel)


    override suspend fun addFilm(id: Int): Result<Unit> {
        return remote.getFilmById(id).map { remoteFilm ->
            mappers.responseToModel(remoteFilm).map {
                local.addFilm(it)
            }
        }
    }

    override suspend fun delete(id: Int): Result<Unit> {
        return getById(id).map {
            local.delete(it)
        }
    }
}
