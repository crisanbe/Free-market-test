package com.cvelez.freemarkettest.feactureItemDetail.data.repository.dataSourceImpl

import com.cvelez.freemarkettest.core.network.api.ApiService
import com.cvelez.freemarkettest.feactureItemDetail.data.model.ArticleDetail
import com.cvelez.freemarkettest.feactureItemDetail.data.repository.dataSource.ArticleDetailDataSource
import retrofit2.Response
import javax.inject.Inject

class ArticleDetailDataSourceImpl @Inject constructor(private val apiService: ApiService) : ArticleDetailDataSource {
    override suspend fun getProductDetail(productId: String): Response<ArticleDetail?> {
        return apiService.getItemDetail(productId = productId)
    }
}