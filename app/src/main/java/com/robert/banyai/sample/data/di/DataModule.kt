package com.robert.banyai.sample.data.di

import com.robert.banyai.sample.BuildConfig
import com.robert.banyai.sample.data.remote.client.RestApiClient
import com.robert.banyai.sample.data.remote.interceptor.RestHeadersInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.Clock
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Named("cacheTTL")
    fun provideInMemoryCacheTTL(): Long = TimeUnit.MINUTES.toMillis(20)

    @Provides
    @Named("apiAuthToken")
    fun provideApiAuthToken(): String = BuildConfig.API_AUTH_TOKEN

    @Provides
    @Named("baseImageUrl")
    fun provideBaseImageUrl(): String = BuildConfig.BASE_IMAGE_URL

    @Singleton
    @Provides
    fun provideSystemClock(): Clock = Clock.systemUTC()

    // region OkHttp

    @Singleton
    @Named("restOkHttpClient")
    @Provides
    fun provideRestOkHttpClient(
        restHeadersInterceptor: RestHeadersInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .connectTimeout(80, TimeUnit.SECONDS)
        .readTimeout(80, TimeUnit.SECONDS)
        .writeTimeout(80, TimeUnit.SECONDS)
        .addInterceptor(restHeadersInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()
    // region Interceptors / Authenticator

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
    }

    // endregion

    // region Gson

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    // endregion

    // region Retrofit

    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("restOkHttpClient") okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    // endregion

    // region API

    @Singleton
    @Provides
    fun provideRestApiClient(retrofit: Retrofit): RestApiClient {
        return retrofit.create(RestApiClient::class.java)
    }

    // endregion
}
