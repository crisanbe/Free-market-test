package com.cvelez.freemarkettest.core.network.wraps

sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val error: ErrorWrapper, val data: T? = null) : ApiResult<T>()
}

sealed class ErrorWrapper {
    object ServiceNotAvailable : ErrorWrapper()
    data class ServiceInternalError(val statusCode: Int, val message: String) : ErrorWrapper()
    object UnknownError : ErrorWrapper()
}