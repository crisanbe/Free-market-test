package com.cvelez.freemarkettest.articleDetailTest.data.repository

import com.cvelez.freemarkettest.core.network.wraps.ApiResult
import com.cvelez.freemarkettest.core.network.wraps.BugWrapper
import com.cvelez.freemarkettest.feactureArticleDetail.data.model.ArticleDetail
import com.cvelez.freemarkettest.feactureArticleDetail.data.repository.ArticleDetailRepositoryImpl
import com.cvelez.freemarkettest.feactureArticleDetail.data.repository.dataSource.ArticleDetailDataSource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import retrofit2.Response

class ArticleDetailRepositoryImplTest {
    private lateinit var articleDetailRepositoryImpl: ArticleDetailRepositoryImpl
    private lateinit var productDetailDataSource: ArticleDetailDataSource

    @Before
    fun setUp() {
        productDetailDataSource = mock { }
        articleDetailRepositoryImpl = ArticleDetailRepositoryImpl(productDetailDataSource)
    }

    @Test
    fun getProductDetail_dataSource_returns_success() = runBlocking {
        whenever(productDetailDataSource.getProductDetail("1")).thenReturn(Response.success(ArticleDetail(id = "1")))
        val result = articleDetailRepositoryImpl.getProductDetail("1")
        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        assertThat(result.data).isEqualTo(ArticleDetail(id = "1"))
    }

    @Test
    fun getProductDetail_datasource_returns_serverError() = runBlocking {
        whenever(productDetailDataSource.getProductDetail("1")).thenReturn(Response.error(500,"error".toResponseBody()))
        val result = articleDetailRepositoryImpl.getProductDetail("1")
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result.errorWrapper).isInstanceOf(BugWrapper.ServiceInternalError::class.java)
    }

    @Test
    fun getProductDetail_datasource_returns_ServiceNotAvailableError() = runBlocking {
        whenever(productDetailDataSource.getProductDetail("1")).thenReturn(Response.error(503,"error".toResponseBody()))
        val result = articleDetailRepositoryImpl.getProductDetail("1")
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result.errorWrapper).isInstanceOf(BugWrapper.ServiceNotAvailable::class.java)
    }

    @Test
    fun searchProduct_datasource_returns_unknownError() = runBlocking {
        whenever(productDetailDataSource.getProductDetail("1")).thenReturn(Response.error(404,"error".toResponseBody()))
        val result = articleDetailRepositoryImpl.getProductDetail("1")
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result.errorWrapper).isInstanceOf(BugWrapper.UnknownError::class.java)
    }

    @Test
    fun searchProduct_datasource_throws_exception_returns_ServiceNotAvailableError() = runBlocking {
        doThrow(RuntimeException()).`when`(productDetailDataSource).getProductDetail("1")
        val result = articleDetailRepositoryImpl.getProductDetail("1")
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat(result.errorWrapper).isInstanceOf(BugWrapper.UnknownError::class.java)
    }
}