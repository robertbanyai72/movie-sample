package com.robert.banyai.sample.data.di

import com.robert.banyai.sample.data.datasource.local.MovieLocalDataSourceImpl
import com.robert.banyai.sample.data.datasource.remote.MovieRemoteDataSourceImpl
import com.robert.banyai.sample.data.local.memory.InMemoryCache
import com.robert.banyai.sample.data.local.memory.InMemoryCacheImpl
import com.robert.banyai.sample.data.remote.handler.RestApiCallHandler
import com.robert.banyai.sample.data.remote.handler.RestApiCallHandlerImpl
import com.robert.banyai.sample.domain.datasource.local.MovieLocalDataSource
import com.robert.banyai.sample.domain.datasource.remote.MovieRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractDataModule {

    // region DataSource

    // region Remote

    @Binds
    @Singleton
    abstract fun bindMovieRemoteDataSource(
        movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl
    ): MovieRemoteDataSource

    // endregion

    // region Local

    @Binds
    @Singleton
    abstract fun bindMovieLocalDataSource(
        movieLocalDataSourceImpl: MovieLocalDataSourceImpl
    ): MovieLocalDataSource

    @Binds
    @Singleton
    abstract fun bindInMemoryCache(
        inMemoryCacheImpl: InMemoryCacheImpl
    ): InMemoryCache

    // region Remote

    @Binds
    @Singleton
    abstract fun bindRestApiCallHandler(
        restApiCallHandlerImpl: RestApiCallHandlerImpl
    ): RestApiCallHandler

    // endregion
}
