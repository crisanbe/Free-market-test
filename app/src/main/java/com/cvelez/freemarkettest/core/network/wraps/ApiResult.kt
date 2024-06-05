package com.cvelez.freemarkettest.core.network.wraps

sealed class ApiResult<T>(val data: T? = null, val errorWrapper: BugWrapper? = null) {
    class Success<T>(data: T) : ApiResult<T>(data)
    class Error<T>(errorWrapper: BugWrapper, data: T? = null) : ApiResult<T>(data, errorWrapper)
}

sealed class BugWrapper(val statusCode: Int? = null) {
    data object ServiceNotAvailable : BugWrapper()
    data class ServiceInternalError(val statusCodeError: Int, val resultErrorMessage: String) : BugWrapper(statusCode = statusCodeError)
    data object UnknownError : BugWrapper()
}