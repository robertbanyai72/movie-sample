package com.robert.banyai.sample.domain.repository

import com.robert.banyai.sample.domain.datasource.local.MovieLocalDataSource
import com.robert.banyai.sample.domain.datasource.remote.MovieRemoteDataSource
import com.robert.banyai.sample.domain.model.Movie
import javax.inject.Inject

interface MovieRepository {
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getMovie(id: Int): Movie?
}

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override suspend fun getTopRatedMovies(): List<Movie> {
        return if (localDataSource.isTopRatedMoviesUpdateNeeded()) {
            remoteDataSource.getTopRatedMovies().also {
                localDataSource.updateTopRatedMovies(it)
            }
        } else {
            localDataSource.getTopRatedMovies()
        }
    }

    override suspend fun getMovie(id: Int): Movie? {
        return if (localDataSource.isTopRatedMoviesUpdateNeeded()) {
            remoteDataSource.getMovie(id)
        } else {
            localDataSource
                .getTopRatedMovies()
                .firstOrNull { item ->
                    item.id == id
                }
        }
    }
}