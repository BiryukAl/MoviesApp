package ru.kpfu.itis.feature.popular.impl.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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
        return flow<List<Film>> {

            val favoriteFilms = local.getAllFavorite().first().getOrElse { listOf() }

            network.getPopularFilms(page).fold(
                onSuccess = { response ->
                    val films = response.films
                    if (films.isNullOrEmpty())
                        throw ConnectionError()
                    else {
                        emit(
                            mapper.listResponseToModel(
                                films,
                                favoriteFilms.map { favoriteFilm -> favoriteFilm.kinopoiskId }
                            ).getOrElse { listOf() }
                        )
                    }
                },
                onFailure = {
                    throw it
                }
            )
        }
    }
}

internal class PopularRepositoryTest : PopularRepository {
    override suspend fun getPopularFilms(page: Int): Flow<List<Film>> {
        TODO("Not yet implemented")
    }
}
