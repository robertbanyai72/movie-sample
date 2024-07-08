package com.robert.banyai.sample.data.remote.model

import com.robert.banyai.sample.domain.model.LayerFailure.DataFailure

sealed class ApiFailure : Throwable() {
    object RequestTimeoutError : ApiFailure()
    object TooManyRequestsError : ApiFailure()
    object ForbiddenError : ApiFailure()
    object UnauthorizedError : ApiFailure()
    object NetworkError : ApiFailure()
    data class ServiceError(val internalCode: Int, val internalMessage: String?) : ApiFailure()
}

fun ApiFailure.toDataFailure(): DataFailure {
    return when (this) {
        is ApiFailure.RequestTimeoutError -> DataFailure.RequestTimeout
        is ApiFailure.TooManyRequestsError -> DataFailure.TooManyRequests
        is ApiFailure.ForbiddenError -> DataFailure.VersionNotSupported
        is ApiFailure.UnauthorizedError -> DataFailure.Unauthorized
        is ApiFailure.NetworkError -> DataFailure.Network

        is ApiFailure.ServiceError -> {
            DataFailure.Custom(
                internalCode = internalCode,
                internalMessage = internalMessage
            )
        }
    }
}
