package ru.kpfu.itis.feature.search.impl.data

import ru.kpfu.itis.feature.search.api.Film

internal class Mapper {

    fun responseToModel(response: SearchResponse.Film): Result<Film> = kotlin.runCatching {
        Film(
            id = checkNotNull(response.filmId),
            response.nameRu,
            preview = response.posterUrlPreview,
            genre = response.genres?.first()?.genre.toString(),
            year = response.year
        )
    }
}
