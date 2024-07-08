package com.robert.banyai.sample.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class RestApiError(
    @SerializedName("detail")
    val message: String
)
