package com.cvelez.freemarkettest.search_article.data.repository.dataSourceImpl

import com.cvelez.freemarkettest.core.network.api.ApiService
import com.cvelez.freemarkettest.featureSearch.data.model.Article
import com.cvelez.freemarkettest.featureSearch.data.model.SearchArticleResult
import com.cvelez.freemarkettest.featureSearch.data.repository.dataSourceImpl.SearchArticleDataSourceImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class SearchArticleDataSourceImplTest {
    private lateinit var searchArticleDataSourceImpl: SearchArticleDataSourceImpl
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = mock { }
        searchArticleDataSourceImpl = SearchArticleDataSourceImpl(apiService)
    }

    @Test
    fun searchProduct_returns_correct_result() = runBlocking {
        whenever(apiService.searchItems("tesing")).thenReturn(Response.success(SearchArticleResult(query = "tesing", results = arrayListOf(Article(id = "1", title = "Producto 1", thumbnail = "priebas")))))
        val result = searchArticleDataSourceImpl.searchArticle("tesing")
        assertThat(result.isSuccessful).isEqualTo(true)
        assertThat(result.body()).isEqualTo(SearchArticleResult(query = "tesing", results = arrayListOf(Article(id = "1", title = "Producto 1", thumbnail = "priebas"))))
    }

    @Test
    fun searchProduct_empty_query_returns_empty_result() = runBlocking {
        whenever(apiService.searchItems("")).thenReturn(Response.success(SearchArticleResult(query = "", results = arrayListOf())))
        val result = searchArticleDataSourceImpl.searchArticle("")
        assertThat(result.isSuccessful).isEqualTo(true)
        assertThat(result.body()).isEqualTo(SearchArticleResult(query = "", results = arrayListOf()))
    }

    @Test
    fun searchProduct_returns_error() = runBlocking {
        whenever(apiService.searchItems("tesing")).thenReturn(Response.error(500, ResponseBody.create("application/json; charset=utf-8".toMediaType(),"")))
        val result = searchArticleDataSourceImpl.searchArticle("tesing")
        assertThat(result.isSuccessful).isEqualTo(false)
    }
}