package com.robert.banyai.sample.domain.di

import com.robert.banyai.sample.domain.logger.logE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @Provides
    @Singleton
    @ApplicationScope
    fun provideCoroutineExceptionHandler(): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            logE(TAG, "provideCoroutineExceptionHandler()", throwable)
        }

    @Provides
    @Singleton
    @ApplicationScope
    fun provideCoroutineScope(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        @ApplicationScope exceptionHandler: CoroutineExceptionHandler
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher + exceptionHandler)

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    private const val TAG = "CoroutinesModule"
}
