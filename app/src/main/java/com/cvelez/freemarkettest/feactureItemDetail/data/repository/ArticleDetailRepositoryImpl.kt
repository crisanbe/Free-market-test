package com.cvelez.freemarkettest.feactureItemDetail.data.repository

import android.util.Log
import com.cvelez.freemarkettest.core.network.wraps.ApiResult
import com.cvelez.freemarkettest.feactureItemDetail.data.model.ArticleDetail
import com.cvelez.freemarkettest.feactureItemDetail.data.repository.dataSource.ArticleDetailDataSource
import com.cvelez.freemarkettest.feactureItemDetail.domain.ArticleDetailRepository
import com.cvelez.freemarkettest.featureSearch.data.repository.toApiResult
import com.cvelez.freemarkettest.featureSearch.data.repository.toErrorWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleDetailRepositoryImpl @Inject constructor(private val productDetailDataSource: ArticleDetailDataSource) :ArticleDetailRepository{
    override suspend fun getProductDetail(productId: String): ApiResult<ArticleDetail?> {
        return withContext(Dispatchers.IO){
            try {
                productDetailDataSource.getProductDetail(productId = productId).toApiResult()
            } catch (e: Exception) {
                Log.e("error", "getProductDetail: ", e)
                ApiResult.Error(e.toErrorWrapper())
            }
        }
    }
}