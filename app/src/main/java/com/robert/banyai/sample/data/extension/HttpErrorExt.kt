package com.robert.banyai.sample.data.extension

import com.google.gson.Gson
import com.robert.banyai.sample.data.remote.model.ApiFailure
import com.robert.banyai.sample.data.remote.util.HttpStatusCode
import retrofit2.Response
import com.robert.banyai.sample.data.remote.model.response.RestApiError

internal fun processHttpFailure(statusCode: Int, message: String?): ApiFailure {
    return when (statusCode) {
        HttpStatusCode.ClientError.Unauthorized.code -> ApiFailure.UnauthorizedError
        HttpStatusCode.ClientError.Forbidden.code -> ApiFailure.ForbiddenError
        HttpStatusCode.ClientError.RequestTimeout.code -> ApiFailure.RequestTimeoutError
        HttpStatusCode.ClientError.TooManyRequests.code -> ApiFailure.TooManyRequestsError
        else -> ApiFailure.ServiceError(internalCode = statusCode, internalMessage = message)
    }
}

internal fun Response<*>.parseHttpErrorBody(): String? {
    return try {
        val error = Gson().fromJson(errorBody()!!.string(), RestApiError::class.java)
        error.message
    } catch (ex: Throwable) {
        null
    }
}
