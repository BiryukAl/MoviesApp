package ru.kpfu.itis.feature.details.api

data class FilmDetail(
    val kinopoiskId: Int,
    val name: String?,
    val imageUrl: String?,
    val genres: List<String>?,
    val countries: List<String>?,
    val year: Int?,
    val description: String?
)
