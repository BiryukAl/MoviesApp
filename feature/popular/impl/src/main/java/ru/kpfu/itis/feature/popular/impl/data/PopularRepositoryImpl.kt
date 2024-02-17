package ru.kpfu.itis.feature.popular.impl.data

import kotlinx.coroutines.flow.first
import ru.kpfu.itis.core.network.ConnectionError
import ru.kpfu.itis.feature.favorite.api.FavoriteFilmRepository
import ru.kpfu.itis.feature.popular.api.Film
import ru.kpfu.itis.feature.popular.api.PopularRepository

internal class PopularRepositoryImpl(
    private val network: PopularFilmDataSource,
    private val local: FavoriteFilmRepository,
    private val mapper: Mappers,
) : PopularRepository {

    override suspend fun getPopularFilms(page: Int): Result<List<Film>> {

        val favoriteFilm = local.getAllFavorite().first().getOrElse { listOf() }

        return network.getPopularFilms(page).flatMap {

            if (it.films.isNullOrEmpty()) return Result.failure(ConnectionError())

            mapper.listResponseToModel(
                it.films,
                favoriteFilm.map { favoriteFilm -> favoriteFilm.kinopoiskId })
        }
    }
}
