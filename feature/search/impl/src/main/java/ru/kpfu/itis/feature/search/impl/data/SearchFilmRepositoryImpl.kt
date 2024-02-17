package ru.kpfu.itis.feature.search.impl.data

import ru.kpfu.itis.feature.search.api.Film
import ru.kpfu.itis.feature.search.api.SearchFilmRepository
import ru.kpfu.itis.feature.search.impl.Mapper

internal class SearchFilmRepositoryImpl(
    private val network: SearchFilmDataSource,
    private val mapper: Mapper
) : SearchFilmRepository {

    override suspend fun getFilmByQuery(query: String): Result<List<Film>> {
        return network.getFilmByQuery(query).map { response: SearchResponse ->
                response.films.orEmpty().filterNotNull().mapNotNull { film ->
                    mapper.responseToModel(film).getOrNull()
                }
            }
    }
}
