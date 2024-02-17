package ru.kpfu.itis.feature.favorite.api

data class FavoriteFilm(
    val kinopoiskId: Int,
    val name: String?,
    val previewUrl: String?,
    val imageUrl: String?,
    val genres: List<String>?,
    val countries: List<String>?,
    val year: Int?,
    val description: String?
)
