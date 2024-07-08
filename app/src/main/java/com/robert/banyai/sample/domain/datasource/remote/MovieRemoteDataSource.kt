package com.robert.banyai.sample.domain.datasource.remote

import com.robert.banyai.sample.domain.model.Movie

interface MovieRemoteDataSource {
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getMovie(id : Int): Movie
}
