package ru.kpfu.itis.feature.search.impl.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import ru.kpfu.itis.core.network.ConnectionError

internal interface SearchFilmDataSource {

    suspend fun getFilmByQuery(query: String): Result<SearchResponse>

    class Base(
        private val client: HttpClient
    ) : SearchFilmDataSource {

        override suspend fun getFilmByQuery(query: String): Result<SearchResponse> {
            val response = client.get("/api/v2.1/films/search-by-keyword") {
                url {
                    parameters.append("keyword", query)
                    parameters.append("page", "1")
                }
            }

            return if (response.status.isSuccess()) {
                try {
                    Result.success(response.body<SearchResponse>())
                } catch (ex: Exception) {
                    Result.failure(ex)
                }
            } else {
                Result.failure(ConnectionError())
            }
        }
    }

    class  Test(): SearchFilmDataSource {
        override suspend fun getFilmByQuery(query: String): Result<SearchResponse> {
            TODO("Not yet implemented")
        }
    }

}
