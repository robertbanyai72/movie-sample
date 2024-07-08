package com.robert.banyai.sample.data.remote.util

sealed class HttpStatusCode(open val code: Int) {

    sealed class Success(override val code: Int) : HttpStatusCode(code) {
        object Ok : Success(200)
        object Created : Success(201)
        object NoContent : Success(204)
    }

    sealed class ClientError(override val code: Int) : HttpStatusCode(code) {
        object BadRequest : ClientError(400)
        object Unauthorized : ClientError(401)
        object Forbidden : ClientError(403)
        object NotFound : ClientError(404)
        object RequestTimeout : ClientError(408)
        object TooManyRequests : ClientError(429)
    }

    sealed class ServerError(override val code: Int) : HttpStatusCode(code) {
        object InternalServerError : ServerError(500)
        object BadGateway : ServerError(502)
        object ServiceUnavailable : ServerError(503)
        object GatewayTimeout : ServerError(504)
    }
}
