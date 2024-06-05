package com.cvelez.freemarkettest.articleDetailTest.data.repository.dataSourceImpl

import com.cvelez.freemarkettest.core.network.api.ApiService
import com.cvelez.freemarkettest.feactureArticleDetail.data.model.ArticleDetail
import com.cvelez.freemarkettest.feactureArticleDetail.data.repository.dataSourceImpl.ArticleDetailDataSourceImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class ArticleDetailDataSourceImplTest {
    private lateinit var articleDetailDataSourceImpl : ArticleDetailDataSourceImpl
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = mock { }
        articleDetailDataSourceImpl = ArticleDetailDataSourceImpl(apiService)
    }

    @Test
    fun getProductDetail_returns_success() = runBlocking {
        whenever(apiService.getItemDetail("1")).thenReturn(Response.success(ArticleDetail(id = "1")))
        val result = articleDetailDataSourceImpl.getProductDetail("1")
        assertThat(result.isSuccessful).isEqualTo(true)
        assertThat(result.body()).isEqualTo(ArticleDetail(id = "1"))
    }

    @Test
    fun getProductDetail_returns_error() = runBlocking {
        whenever(apiService.getItemDetail("1")).thenReturn(Response.error(500, ResponseBody.create("application/json; charset=utf-8".toMediaType(),"")))
        val result = articleDetailDataSourceImpl.getProductDetail("1")
        assertThat(result.isSuccessful).isEqualTo(false)
    }
}