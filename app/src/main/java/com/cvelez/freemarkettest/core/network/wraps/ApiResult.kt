package com.cvelez.freemarkettest.core.network.wraps

sealed class ApiResult<T>(
    val data: T? = null,
    val errorWrapper: ErrorWrapper? = null
) {
    class Success<T>(data: T) : ApiResult<T>(data)
    class Error<T>(errorWrapper: ErrorWrapper, data: T? = null) : ApiResult<T>(data, errorWrapper)
}

sealed class ErrorWrapper(val errorMessage: String?,
                          val statusCode: Int? = null) {
    object ServiceNotAvailable : ErrorWrapper("No internet connection")
    data class ServiceInternalError(val statusCodeError: Int, val resultErrorMessage: String) : ErrorWrapper(resultErrorMessage,statusCode = statusCodeError)
    object UnknownError : ErrorWrapper("Unknown error")
}