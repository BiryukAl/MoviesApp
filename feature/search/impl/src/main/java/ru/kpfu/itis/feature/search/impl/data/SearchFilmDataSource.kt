package ru.kpfu.itis.feature.search.impl.data

import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import ru.kpfu.itis.core.network.getResult

internal interface SearchFilmDataSource {

    suspend fun getFilmByQuery(query: String): Result<SearchResponse>

    class Base(
        private val client: HttpClient
    ) : SearchFilmDataSource {

        override suspend fun getFilmByQuery(query: String): Result<SearchResponse> {
            return client.getResult("/api/v2.1/films/search-by-keyword") {
                method = HttpMethod.Delete
                url {
                    url {
                        parameters.append("keyword", query)
                        parameters.append("page", "1")
                    }
                }
            }
        }
    }

}
