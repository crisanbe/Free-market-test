package com.cvelez.freemarkettest.featureSearch.data.repository.dataSourceImpl

import com.cvelez.freemarkettest.core.network.api.ApiService
import com.cvelez.freemarkettest.featureSearch.data.model.SearchArticleResult
import com.cvelez.freemarkettest.featureSearch.data.repository.dataSource.SearchArticleDataSource
import retrofit2.Response
import javax.inject.Inject

class SearchArticleDataSourceImpl @Inject constructor(private val apiService: ApiService) : SearchArticleDataSource {
    override suspend fun searchArticle(query:String): Response<SearchArticleResult?> {
        return apiService.searchItems(query)
    }
}