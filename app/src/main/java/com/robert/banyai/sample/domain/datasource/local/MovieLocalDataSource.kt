package com.robert.banyai.sample.domain.datasource.local

import com.robert.banyai.sample.domain.model.Movie

interface MovieLocalDataSource {
    fun updateTopRatedMovies(items: List<Movie>)
    fun isTopRatedMoviesUpdateNeeded(): Boolean
    fun getTopRatedMovies(): List<Movie>
}
