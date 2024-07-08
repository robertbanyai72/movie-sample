package com.robert.banyai.sample.domain.usecase

import com.robert.banyai.sample.domain.model.Movie
import com.robert.banyai.sample.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovie @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(id: Int): Movie? = movieRepository.getMovie(id)
}