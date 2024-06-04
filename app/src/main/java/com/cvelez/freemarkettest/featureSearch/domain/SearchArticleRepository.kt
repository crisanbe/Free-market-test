package com.cvelez.freemarkettest.featureSearch.domain

import com.cvelez.freemarkettest.core.network.wraps.ApiResult
import com.cvelez.freemarkettest.featureSearch.data.model.SearchArticleResult

interface SearchArticleRepository {
    suspend fun searchArticle(query:String) : ApiResult<SearchArticleResult?>
}