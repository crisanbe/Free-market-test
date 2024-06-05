package com.cvelez.freemarkettest.core.network.api.utils

import com.cvelez.freemarkettest.core.network.wraps.BugWrapper
import retrofit2.Response
import java.net.HttpURLConnection

object ErrorUtils {
    fun getApiError(response: Response<*>): BugWrapper {
        return when (response.code()) {
            HttpURLConnection.HTTP_UNAVAILABLE -> BugWrapper.ServiceNotAvailable
            HttpURLConnection.HTTP_INTERNAL_ERROR -> BugWrapper.ServiceInternalError(
                response.code(), response.errorBody()?.string() ?: "Internal Server Error"
            )
            else -> BugWrapper.UnknownError
        }
    }
}