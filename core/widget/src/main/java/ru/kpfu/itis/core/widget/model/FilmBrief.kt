package ru.kpfu.itis.core.widget.model

data class FilmBrief(
    val filmId: Int,
    val nameRu: String,
    val posterUrlPreview: String,
    val year: String,
    val genre: String,
    val isFavorite: Boolean = false
)
