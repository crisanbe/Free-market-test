package com.cvelez.freemarkettest.core.network.api.utils

import android.util.Log
import com.cvelez.freemarkettest.core.network.wraps.ErrorWrapper
import retrofit2.Response
import java.net.HttpURLConnection

object ErrorUtils {
    fun getApiError(response: Response<*>): ErrorWrapper {
        Log.e("error", "getApiError: ${response.code()} ${response.message()} ${response.errorBody()}")
        return when (response.code()) {
            HttpURLConnection.HTTP_UNAVAILABLE -> ErrorWrapper.ServiceNotAvailable
            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                val errorMessage = response.errorBody()?.string() ?: "Internal Server Error"
                ErrorWrapper.ServiceInternalError(response.code(), errorMessage)
            }
            else -> ErrorWrapper.UnknownError
        }
    }
}
