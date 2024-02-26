package ru.kpfu.itis.feature.favorite.impl.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class FilmResponse(
    @SerialName("countries")
    val countries: List<Country?>?,
    @SerialName("description")
    val description: String?,
    val genres: List<Genre?>?,
    @SerialName("kinopoiskId")
    val kinopoiskId: Int?,
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
