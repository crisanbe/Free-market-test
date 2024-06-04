package com.cvelez.freemarkettest.featureSearch.data.repository

import com.cvelez.freemarkettest.core.network.api.utils.ErrorUtils.getApiError
import com.cvelez.freemarkettest.core.network.wraps.ApiResult
import com.cvelez.freemarkettest.core.network.wraps.ErrorWrapper
import com.cvelez.freemarkettest.featureSearch.data.model.SearchArticleResult
import com.cvelez.freemarkettest.featureSearch.data.repository.dataSource.SearchArticleDataSource
import com.cvelez.freemarkettest.featureSearch.domain.SearchArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.net.SocketException
import javax.inject.Inject

class SearchArticleRepositoryImpl @Inject constructor(private val searchProductDataSource: SearchArticleDataSource) :SearchArticleRepository {
    override suspend fun searchArticle(query:String): ApiResult<SearchArticleResult?> {
        return withContext(Dispatchers.IO) {
            try {
                searchProductDataSource.searchArticle(query).toApiResult()
            } catch (e: Exception) {
                ApiResult.Error(e.toErrorWrapper())
            }
        }
    }
}

fun <T> Response<T>.toApiResult() : ApiResult<T?> {
    return if (this.isSuccessful) {
        ApiResult.Success(this.body())
    } else {
        ApiResult.Error(getApiError(this))
    }
}

fun Exception.toErrorWrapper(): ErrorWrapper {
    return when (this) {
        is IOException, is SocketException -> ErrorWrapper.ServiceNotAvailable
        else -> ErrorWrapper.UnknownError
    }
}
