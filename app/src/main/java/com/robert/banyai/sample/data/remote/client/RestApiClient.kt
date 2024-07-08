package com.robert.banyai.sample.data.remote.client

import com.robert.banyai.sample.data.remote.model.response.ApiMovie
import com.robert.banyai.sample.data.remote.model.response.ApiTopRatedMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApiClient {

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(): Response<ApiTopRatedMovies>

    @GET("3/movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") movieId: Int): Response<ApiMovie>
}
