package ru.kpfu.itis.feature.popular.api


data class Film(
    val id: Int,
    val name: String?,
    val preview: String?,
    val genre: String?,
    val year: String?,
    val isFavorite: Boolean?,
)
