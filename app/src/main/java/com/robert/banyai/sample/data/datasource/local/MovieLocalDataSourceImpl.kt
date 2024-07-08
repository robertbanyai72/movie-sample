package com.robert.banyai.sample.data.datasource.local

import com.robert.banyai.sample.data.local.memory.InMemoryCache
import com.robert.banyai.sample.domain.datasource.local.MovieLocalDataSource
import com.robert.banyai.sample.domain.model.Movie
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val inMemoryCache: InMemoryCache
) : MovieLocalDataSource {

    override fun updateTopRatedMovies(items: List<Movie>) {
        inMemoryCache.cacheTopRatedMovies(items)
    }

    override fun isTopRatedMoviesUpdateNeeded(): Boolean =
        inMemoryCache.isTopRatedMoviesUpdateNeeded()

    override fun getTopRatedMovies(): List<Movie> =
        inMemoryCache.getTopRatedMovies() ?: listOf()
}