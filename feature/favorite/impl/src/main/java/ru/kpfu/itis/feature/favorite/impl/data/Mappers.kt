package ru.kpfu.itis.feature.favorite.impl.data

import io.realm.kotlin.ext.realmSetOf
import ru.kpfu.itis.core.db.realm.Film
import ru.kpfu.itis.feature.favorite.api.FavoriteFilm

class Mappers {

    fun entityToModel(entity: Film?): Result<FavoriteFilm> = kotlin.runCatching {
        if (entity == null) return Result.failure(NullPointerException())
        with(entity) {
            FavoriteFilm(
                kinopoiskId = kinopoiskId,
                name = name,
                previewUrl = previewUrl,
                imageUrl = imageUrl,
                genres = genres.toList(),
                year = year,
                description = description,
                countries = countries.toList(),
            )
        }
    }


    fun modelToEntity(model: FavoriteFilm): Result<Film> = kotlin.runCatching {
        Film().apply {
            kinopoiskId = model.kinopoiskId
            name = model.name ?: ""
            previewUrl = model.previewUrl ?: ""
            imageUrl = model.imageUrl ?: ""
            genres = realmSetOf<String>().apply { addAll(model.genres?.toSet() ?: setOf()) }
            countries = realmSetOf<String>().apply { addAll(model.countries?.toSet() ?: setOf()) }
            year = model.year ?: -1
            description = model.description ?: ""
        }
    }

    fun responseToModel(response: FilmResponse): Result<FavoriteFilm> = kotlin.runCatching {
        with(response) {
            FavoriteFilm(
                kinopoiskId = checkNotNull(kinopoiskId),
                name = nameRu,
                previewUrl = posterUrlPreview,
                imageUrl = posterUrl,
                genres = genres?.filterNotNull()?.mapNotNull { it.genre }?.toList(),
                countries = countries?.filterNotNull()?.mapNotNull { it.country }?.toList(),
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
