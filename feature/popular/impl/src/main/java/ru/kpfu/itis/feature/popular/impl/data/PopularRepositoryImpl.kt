package ru.kpfu.itis.feature.popular.impl.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import ru.kpfu.itis.core.network.ConnectionError
import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository
import ru.kpfu.itis.feature.popular.api.Film
import ru.kpfu.itis.feature.popular.api.PopularRepository

internal class PopularRepositoryImpl(
    private val network: PopularFilmDataSource,
    private val local: FavoriteFilmRepository,
    private val mapper: Mappers,
) : PopularRepository {

    override suspend fun getPopularFilms(page: Int): Flow<List<Film>> {
        val favoriteFilms = local.getAllFavorite()

        val popularFilms = flow {
            emit(network.getPopularFilms(page))
        }
        return popularFilms.combine(favoriteFilms) { populars, favorites ->
            val films = populars.getOrThrow().films
            if (films.isNullOrEmpty())
                throw ConnectionError()
            else {
                mapper.listResponseToModel(
                    films,
                    favorites.getOrDefault(listOf()).map { film -> film.kinopoiskId }
                ).getOrElse { listOf() }
            }
        }
    }
}

internal class PopularRepositoryTest : PopularRepository {
    override suspend fun getPopularFilms(page: Int): Flow<List<Film>> {
        TODO("Not yet implemented")
    }
}
