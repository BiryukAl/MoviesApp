package ru.kpfu.itis.feature.search.impl

import ru.kpfu.itis.feature.search.api.Film
import ru.kpfu.itis.feature.search.impl.data.SearchResponse

class Mapper {

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