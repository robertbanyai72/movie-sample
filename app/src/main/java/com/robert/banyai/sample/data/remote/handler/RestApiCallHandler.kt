package com.robert.banyai.sample.data.remote.handler

import retrofit2.Response
import com.robert.banyai.sample.data.remote.model.response.RestResponse

interface RestApiCallHandler {
    suspend fun <T : RestResponse> process(apiCall: suspend () -> Response<T>): Result<T>
}
