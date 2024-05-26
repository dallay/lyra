package com.lyra.app.users.domain

import com.lyra.common.domain.bus.query.Response

enum class ApiResponseStatus {
    SUCCESS,
    FAILURE
}

data class ApiDataResponse<T>(
    val status: ApiResponseStatus,
    val data: T? = null,
    val error: String? = null
) : Response {
    companion object {
        fun <T> success(data: T): ApiDataResponse<T> = ApiDataResponse(ApiResponseStatus.SUCCESS, data)

        fun <T> failure(error: String): ApiDataResponse<T> = ApiDataResponse(ApiResponseStatus.FAILURE, error = error)
    }
}
