package ru.kpfu.itis.feature.search.api

data class Film(
    val id: Int,
    val name: String?,
    val preview: String?,
    val genre: String?,
    val year: String?,
)
