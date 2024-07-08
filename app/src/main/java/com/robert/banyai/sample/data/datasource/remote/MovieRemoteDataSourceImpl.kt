package com.robert.banyai.sample.data.datasource.remote

import com.robert.banyai.sample.data.remote.client.RestApiClient
import com.robert.banyai.sample.data.remote.handler.RestApiCallHandler
import com.robert.banyai.sample.data.remote.model.ApiFailure
import com.robert.banyai.sample.data.remote.model.response.toDomain
import com.robert.banyai.sample.data.remote.model.toDataFailure
import com.robert.banyai.sample.domain.datasource.remote.MovieRemoteDataSource
import com.robert.banyai.sample.domain.model.Movie
import javax.inject.Inject
import javax.inject.Named

class MovieRemoteDataSourceImpl @Inject constructor(
    private val restApiCallHandler: RestApiCallHandler,
    private val restApiClient: RestApiClient,
    @Named("baseImageUrl") private val baseImageUrl: String
) : MovieRemoteDataSource {

    override suspend fun getTopRatedMovies(): List<Movie> = try {
        val response = restApiCallHandler.process {
            restApiClient.getTopRatedMovies()
        }.mapCatching { response ->
            response.results.map { movie ->
                movie.toDomain().copy(
                    posterUrl = "$baseImageUrl${movie.posterPath}"
                )
            }
        }

        response.getOrThrow()
    } catch (failure: ApiFailure) {
        throw failure.toDataFailure()
    }

    override suspend fun getMovie(id: Int): Movie = try {
        val response = restApiCallHandler.process {
            restApiClient.getMovie(id)
        }.mapCatching { response ->
            response.toDomain().copy(
                posterUrl = "$baseImageUrl${response.posterPath}"
            )
        }

        response.getOrThrow()
    } catch (failure: ApiFailure) {
        throw failure.toDataFailure()
    }
}