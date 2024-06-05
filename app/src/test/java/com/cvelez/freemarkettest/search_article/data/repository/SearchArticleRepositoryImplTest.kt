package com.cvelez.freemarkettest.search_article.data.repository

import com.cvelez.freemarkettest.core.network.wraps.ApiResult
import com.cvelez.freemarkettest.core.network.wraps.BugWrapper
import com.cvelez.freemarkettest.featureSearch.data.model.Article
import com.cvelez.freemarkettest.featureSearch.data.model.SearchArticleResult
import com.cvelez.freemarkettest.featureSearch.data.repository.SearchArticleRepositoryImpl
import com.cvelez.freemarkettest.featureSearch.data.repository.dataSource.SearchArticleDataSource
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.HttpURLConnection

class SearchArticleRepositoryImplTest {
    private lateinit var searchArticleRepositoryImpl: SearchArticleRepositoryImpl
    private lateinit var searchProductDataSource: SearchArticleDataSource

    @Before
    fun setUp() {
        searchProductDataSource = mockk()
        searchArticleRepositoryImpl = SearchArticleRepositoryImpl(searchProductDataSource = searchProductDataSource)
    }

    @Test
    fun searchProduct_datasource_returns_success() = runTest {
        coEvery { searchProductDataSource.searchArticle("pruebas") } returns Response.success(SearchArticleResult(query = "pruebas", results = arrayListOf(Article(id = "1", title = "Producto 1", thumbnail = "prueba"))))
        val result = searchArticleRepositoryImpl.searchArticle("pruebas")
        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        assertThat((result as ApiResult.Success).data).isEqualTo(SearchArticleResult(query = "pruebas", results = arrayListOf(Article(id = "1", title = "Producto 1", thumbnail = "prueba"))))
    }

    @Test
    fun searchProduct_datasource_returns_serverErrorOrNull() = runBlocking {
        coEvery { searchProductDataSource.searchArticle("pruebas") } returns Response.error(500, "error".toResponseBody())
        val result = searchArticleRepositoryImpl.searchArticle("pruebas")
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat((result as ApiResult.Error).errorWrapper).isInstanceOf(BugWrapper.ServiceInternalError::class.java)
        assertThat((result.errorWrapper as BugWrapper.ServiceInternalError).statusCode).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
    }

    @Test
    fun searchProduct_datasource_returns_ServiceNotAvailableError() = runTest {
        coEvery { searchProductDataSource.searchArticle("some_query") } throws IOException("Service is not available")
        val result = searchArticleRepositoryImpl.searchArticle("some_query")
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat((result as ApiResult.Error).errorWrapper).isInstanceOf(BugWrapper.ServiceNotAvailable::class.java)
    }

    @Test
    fun searchProduct_datasource_returns_unknownError() = runTest {
        coEvery { searchProductDataSource.searchArticle("pruebas") } returns Response.error(404, "error".toResponseBody())
        val result = searchArticleRepositoryImpl.searchArticle("pruebas")
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat((result as ApiResult.Error).errorWrapper).isInstanceOf(BugWrapper.UnknownError::class.java)
    }

    @Test
    fun searchProduct_datasource_throws_exception_returns_ServiceNotAvailableError() = runTest {
        coEvery { searchProductDataSource.searchArticle("pruebas") } throws RuntimeException()
        val result = searchArticleRepositoryImpl.searchArticle("pruebas")
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat((result as ApiResult.Error).errorWrapper).isInstanceOf(BugWrapper.UnknownError::class.java)
    }
}