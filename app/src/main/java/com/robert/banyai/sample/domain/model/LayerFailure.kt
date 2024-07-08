package com.robert.banyai.sample.domain.model

sealed class LayerFailure : Throwable() {

    abstract class FeatureFailure(open val remoteMessage: String? = null) : LayerFailure()

    sealed class DataFailure : Throwable() {
        object Network : DataFailure()

        object RequestTimeout : DataFailure()

        object TooManyRequests : DataFailure()

        object VersionNotSupported : DataFailure()

        object Unauthorized : DataFailure()

        data class Custom(val internalCode: Int, val internalMessage: String?) : DataFailure()
    }
}
