package ru.kpfu.itis.feature.popular.impl.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class FilmsResponse(
    @SerialName("items")
    val films: List<Film?>?,
    @SerialName("total")
    val total: Int?,
    @SerialName("totalPages")
    val totalPages: Int?
) {
    @Serializable
    data class Film(
        @SerialName("countries")
        val countries: List<Country?>?,
        @SerialName("genres")
        val genres: List<Genre?>?,
        @SerialName("kinopoiskId")
        val kinopoiskId: Int?,
        @SerialName("nameEn")
        val nameEn: String?,
        @SerialName("nameOriginal")
        val nameOriginal: String?,
        @SerialName("nameRu")
        val nameRu: String?,
        @SerialName("posterUrl")
        val posterUrl: String?,
        @SerialName("posterUrlPreview")
        val posterUrlPreview: String?,
        @SerialName("year")
        val year: Int?
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