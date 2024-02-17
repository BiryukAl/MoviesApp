package ru.kpfu.itis.feature.search.impl.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface SearchFilmDataSource {

    suspend fun getFilmByQuery(query: String): Result<SearchResponse>

    class Base(
        private val client: HttpClient
    ) : SearchFilmDataSource {

        override suspend fun getFilmByQuery(query: String): Result<SearchResponse> =
            client.get("films/search-by-keyword") {
                url {
                    parameters.append("keyword", query)
                    parameters.append("page", "1")
                }
            }.body()
    }

    class  Test(): SearchFilmDataSource {
        override suspend fun getFilmByQuery(query: String): Result<SearchResponse> {
            TODO("Not yet implemented")
        }
    }

}
