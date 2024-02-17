package ru.kpfu.itis.feature.details.impl.data

import ru.kpfu.itis.feature.details.api.DetailsFilmRepository
import ru.kpfu.itis.feature.details.api.FilmDetail
import ru.kpfu.itis.feature.favorite.api.FavoriteFilm
import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository

class DetailsFilmRepositoryImpl(
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

class Mapper {
    fun responseToModel(response: FilmResponse): Result<FilmDetail> = kotlin.runCatching {
        with(response) {
            FilmDetail(
                kinopoiskId = checkNotNull(kinopoiskId),
                name = nameRu,
                imageUrl = posterUrl,
                genres = genres?.filterNotNull()?.mapNotNull { it.genre }?.toList(),
                countries = countries?.filterNotNull()?.mapNotNull { it.country }?.toList(),
                year = year,
                description = description,
            )
        }
    }

    fun favoriteToDetails(favoriteFilm: FavoriteFilm): Result<FilmDetail> = kotlin.runCatching {
        with(favoriteFilm) {
            FilmDetail(
                kinopoiskId = kinopoiskId,
                name = name,
                imageUrl = imageUrl,
                genres = genres,
                countries = countries,
                year = year,
                description = description,
            )
        }
    }

}

inline fun <R, T> Result<R>.flatMap(transform: (R) -> Result<T>): Result<T> =
    fold(
        onSuccess = { transform(it) },
        onFailure = { Result.failure(it) }
    )
