package com.cvelez.freemarkettest.featureSearch.data.repository.dataSource

import com.cvelez.freemarkettest.featureSearch.data.model.SearchArticleResult
import retrofit2.Response


interface SearchArticleDataSource {
    suspend fun searchProduct(query:String) : Response<SearchArticleResult?>
}