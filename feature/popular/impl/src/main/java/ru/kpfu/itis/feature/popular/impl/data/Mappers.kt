package ru.kpfu.itis.feature.popular.impl.data

import ru.kpfu.itis.feature.popular.api.Film

internal class Mappers {
    fun responseToModel(response: FilmsResponse.Film, isFavorite: Boolean): Result<Film> =
        kotlin.runCatching {
            Film(
                id = response.kinopoiskId!!,
                name = response.nameRu,
                preview = response.posterUrlPreview,
                genre = response.genres?.first()?.genre,
                year = response.year.toString(),
                isFavorite = isFavorite
            )
        }

    fun listResponseToModel(
        responses: List<FilmsResponse.Film?>,
        isFavoriteList: List<Int>
    ): Result<List<Film>> = kotlin.runCatching {
        responses.filterNotNull().mapNotNull { film ->
            responseToModel(
                response = film,
                isFavorite = isFavoriteList.contains(film.kinopoiskId)
            ).getOrNull()
        }
    }
}

inline fun <R, T> Result<R>.flatMap(transform: (R) -> Result<T>): Result<T> =
    fold(
        onSuccess = { transform(it) },
        onFailure = { Result.failure(it) }
    )

