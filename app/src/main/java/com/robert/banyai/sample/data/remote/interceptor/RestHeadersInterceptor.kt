package com.robert.banyai.sample.data.remote.interceptor

import com.robert.banyai.sample.BuildConfig
import javax.inject.Inject
import javax.inject.Named
import okhttp3.Interceptor
import okhttp3.Response

class RestHeadersInterceptor @Inject constructor(
    @Named("apiAuthToken") private val apiAuthToken: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val modifiedRequest = request
            .newBuilder()
            .run {
                header(
                    HEADER_AUTHORIZATION,
                    "$HEADER_BEARER_PREFIX ${BuildConfig.API_AUTH_TOKEN}"
                )
                build()
            }

        return chain.proceed(modifiedRequest)
    }

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val HEADER_BEARER_PREFIX = "Bearer"
    }
}
