package com.robert.banyai.sample.data.remote.model.response

import com.google.gson.annotations.SerializedName
import com.robert.banyai.sample.domain.model.Movie

data class ApiTopRatedMovies(
    @SerializedName("results")
    val results: List<ApiMovie>
) : RestResponse

data class ApiMovie(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String
) : RestResponse

fun ApiMovie.toDomain() = Movie(
    adult = adult,
    id = id,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title
)