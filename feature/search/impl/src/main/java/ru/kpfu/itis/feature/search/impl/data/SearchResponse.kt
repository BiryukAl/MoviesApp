package ru.kpfu.itis.feature.search.impl.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SearchResponse(
    @SerialName("keyword")
    val keyword: String?,
    @SerialName("pagesCount")
    val pagesCount: Int?,
    @SerialName("films")
    val films: List<Film?>?,
//    @SerialName("searchFilmsCountResult")
//    val searchFilmsCountResult: Int?
) {
    @Serializable
    data class Film(
        @SerialName("countries")
        val countries: List<Country?>?,
        @SerialName("filmId")
        val filmId: Int?,
        @SerialName("genres")
        val genres: List<Genre?>?,
        @SerialName("nameRu")
        val nameRu: String?,
        @SerialName("posterUrlPreview")
        val posterUrlPreview: String?,
        @SerialName("year")
        val year: String?
    ) {
        @Serializable
        data class Country(
            @SerialName("country")
            val country: String?
        )

        @Serializable
        data class Genre(
            @SerialName("genre")
            val genre: String?
        )
    }
}