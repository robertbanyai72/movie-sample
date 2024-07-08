package com.robert.banyai.sample.domain.usecase

import com.robert.banyai.sample.domain.model.Movie
import com.robert.banyai.sample.domain.repository.MovieRepository
import javax.inject.Inject

class GetTopRatedMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(): List<Movie> = movieRepository.getTopRatedMovies()
}
