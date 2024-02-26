package ru.kpfu.itis.feature.search.impl.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kpfu.itis.feature.search.api.Film
import ru.kpfu.itis.feature.search.api.SearchFilmRepository

internal class SearchFilmRepositoryImpl(
    private val network: SearchFilmDataSource,
    private val mapper: Mapper
) : SearchFilmRepository {

    override suspend fun getFilmByQuery(query: String): Flow<List<Film>> = flow {

        network.getFilmByQuery(query).map { response: SearchResponse ->
            val films = response.films.orEmpty()
                .filterNotNull()
                .mapNotNull { film ->
                    mapper.responseToModel(film).getOrNull()
                }
            emit(films)
        }
    }
}
