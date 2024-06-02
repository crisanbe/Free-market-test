package com.cvelez.freemarkettest.core.network.api

import com.cvelez.freemarkettest.Constants.DETAILS_URL
import com.cvelez.freemarkettest.Constants.SEARCH_URL
import com.cvelez.freemarkettest.feactureArticleDetail.data.model.ArticleDetail
import com.cvelez.freemarkettest.featureSearch.data.model.SearchArticleResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(SEARCH_URL)
    suspend fun searchItems(@Query("q") query : String) : Response<SearchArticleResult?>

    @GET(DETAILS_URL)
    suspend fun getItemDetail(@Path("id") productId:String) : Response<ArticleDetail?>
}