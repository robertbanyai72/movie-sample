package com.robert.banyai.sample.data.local.memory

import com.robert.banyai.sample.domain.model.Movie
import java.time.Clock
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.MutableStateFlow

interface InMemoryCache {
    fun cacheTopRatedMovies(value: List<Movie>)

    fun getTopRatedMovies(): List<Movie>?

    fun isTopRatedMoviesUpdateNeeded(): Boolean
}

class InMemoryCacheImpl @Inject constructor(
    private val clock: Clock,
    @Named("cacheTTL") private val ttlMillis: Long
) : InMemoryCache {

    private val _topRatedMovies = MutableStateFlow<CachedValue<List<Movie>>?>(null)

    override fun isTopRatedMoviesUpdateNeeded(): Boolean =
        requiresUpdate(_topRatedMovies.value)

    override fun getTopRatedMovies(): List<Movie>? = _topRatedMovies.value?.value

    override fun cacheTopRatedMovies(value: List<Movie>) {
        _topRatedMovies.value = cacheValue(value)
    }

    private fun <V> cacheValue(value: V): CachedValue<V> = CachedValue(
        expiration = clock.millis().plus(ttlMillis),
        value = value
    )

    private fun requiresUpdate(value: CachedValue<*>?) =
        value == null || clock.millis() >= value.expiration

    private data class CachedValue<V>(
        val expiration: Long,
        val value: V
    )
}
