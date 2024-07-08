package com.robert.banyai.sample.ui.model

import com.robert.banyai.sample.domain.model.Movie

data class PMovie(
    val adult: Boolean,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterUrl: String?,
    val releaseDate: String,
    val title: String
)

fun Movie.toPresentation() = PMovie(
    adult = adult,
    id = id,
    originalTitle = originalTitle,
    overview = overview,
    posterUrl = posterUrl,
    releaseDate = releaseDate,
    title = title
)