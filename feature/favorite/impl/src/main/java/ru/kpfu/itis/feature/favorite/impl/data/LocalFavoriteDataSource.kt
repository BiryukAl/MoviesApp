package ru.kpfu.itis.feature.favorite.impl.data

import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.exceptions.RealmException
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.core.db.realm.Film
import ru.kpfu.itis.feature.favorite.api.FavoriteFilm

internal interface LocalFavoriteDataSource {

    fun getAllFavorite(): Flow<Result<List<Film>>>
    fun getById(kinopoiskId: Int): Result<Film>
    suspend fun addFilm(film: FavoriteFilm): Result<Film>
    suspend fun delete(film: FavoriteFilm): Result<Unit>

    class Base(
        private val realm: Realm,
        private val mapper: Mappers,
    ) : LocalFavoriteDataSource {

        override fun getAllFavorite(): Flow<Result<List<Film>>> {
            return realm.query<Film>().asFlow()
                .map {
                    it.list.toList()
                }.map {
                    if (it.isNotEmpty()) Result.success(it)
                    else Result.failure(Throwable())
                }
        }


        override fun getById(kinopoiskId: Int): Result<Film> {
            return try {

                val film = realm.query<Film>(
                    "kinopoiskId == $0", kinopoiskId
                ).find().firstOrNull()

                return if (film != null) Result.success(film)
                else Result.failure(RealmException())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        }


        override suspend fun addFilm(film: FavoriteFilm): Result<Film> {
            return try {
                val inFilm = mapper.modelToEntity(film).getOrThrow()

                val outFilm = realm.write {
                    copyToRealm(
                        inFilm,
                        updatePolicy = UpdatePolicy.ALL
                    )
                }

                Result.success(outFilm)
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        }

        override suspend fun delete(film: FavoriteFilm): Result<Unit> {
            return try {
                val inFilm = mapper.modelToEntity(film).getOrThrow()

                val out = realm.write {
                    delete(inFilm)
                }

                Result.success(out)
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        }
    }

    class Test() : LocalFavoriteDataSource {

        val db = listOf<Film>()

        override fun getAllFavorite(): Flow<Result<List<Film>>> {
            TODO("Not yet implemented")
        }

        override fun getById(kinopoiskId: Int): Result<Film> {
            TODO("Not yet implemented")
        }

        override suspend fun addFilm(film: FavoriteFilm): Result<Film> {
            TODO("Not yet implemented")
        }

        override suspend fun delete(film: FavoriteFilm): Result<Unit> {
            TODO("Not yet implemented")
        }

    }
}
