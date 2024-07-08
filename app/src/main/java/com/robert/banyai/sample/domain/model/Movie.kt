package com.robert.banyai.sample.domain.model

data class Movie(
    val adult: Boolean,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterUrl: String? = null,
    val posterPath: String?,
    val releaseDate: String,
    val title: String
)