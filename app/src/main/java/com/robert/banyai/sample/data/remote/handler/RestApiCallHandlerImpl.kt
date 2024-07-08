package com.robert.banyai.sample.data.remote.handler

import com.robert.banyai.sample.data.extension.parseHttpErrorBody
import com.robert.banyai.sample.data.extension.processHttpFailure
import com.robert.banyai.sample.data.remote.model.ApiFailure
import com.robert.banyai.sample.data.remote.model.response.RestResponse
import java.net.UnknownHostException
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import com.robert.banyai.sample.domain.di.IoDispatcher

class RestApiCallHandlerImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RestApiCallHandler {

    override suspend fun <T : RestResponse> process(
        apiCall: suspend () -> Response<T>
    ): Result<T> {
        return withContext(dispatcher) {
            try {
                val response = apiCall.invoke()
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    return@withContext Result.success(body)
                } else {
                    return@withContext Result.failure(
                        exception = processHttpFailure(
                            statusCode = response.code(),
                            message = response.parseHttpErrorBody()
                        )
                    )
                }
            } catch (exception: Throwable) {
                val error = when (exception) {
                    is UnknownHostException -> ApiFailure.NetworkError
                    else -> exception
                }

                return@withContext Result.failure(error)
            }
        }
    }
}
