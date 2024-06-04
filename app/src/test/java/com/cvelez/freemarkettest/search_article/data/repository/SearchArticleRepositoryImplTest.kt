package com.cvelez.freemarkettest.search_article.data.repository

import com.cvelez.freemarkettest.core.network.wraps.ApiResult
import com.cvelez.freemarkettest.core.network.wraps.ErrorWrapper
import com.cvelez.freemarkettest.featureSearch.data.model.Article
import com.cvelez.freemarkettest.featureSearch.data.model.SearchArticleResult
import com.cvelez.freemarkettest.featureSearch.data.repository.SearchArticleRepositoryImpl
import com.cvelez.freemarkettest.featureSearch.data.repository.dataSource.SearchArticleDataSource
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class SearchArticleRepositoryImplTest {
    private lateinit var searchArticleRepositoryImpl: SearchArticleRepositoryImpl
    private lateinit var searchProductDataSource: SearchArticleDataSource

    companion object {
        val SEARCH_QUERY = "pruebas"
        val RESULT_LIST = arrayListOf(
            Article(id = "1", title = "Producto 1", thumbnail = "prueba")
        )
        val SEARCH_RESULT = SearchArticleResult(query = SEARCH_QUERY, results = RESULT_LIST)
    }

    @Before
    fun setUp() {
        searchProductDataSource = mockk()
        searchArticleRepositoryImpl = SearchArticleRepositoryImpl(searchProductDataSource = searchProductDataSource)
    }

    @Test
    fun searchProduct_datasource_returns_success() = runTest {
        // Arrange
        coEvery { searchProductDataSource.searchProduct(SEARCH_QUERY) } returns
                Response.success(SEARCH_RESULT)
        // Act
        val result = searchArticleRepositoryImpl.searchProduct(SEARCH_QUERY)
        // Assert
        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        assertThat((result as ApiResult.Success).data).isEqualTo(SEARCH_RESULT)
        coVerify(exactly = 1) { searchProductDataSource.searchProduct(SEARCH_QUERY) }
    }

    @Test
    fun searchProduct_datasource_returns_serverErrorOrNull() {
        // Arrange
        coEvery { searchProductDataSource.searchProduct(SEARCH_QUERY) } returns Response.error(500, "error".toResponseBody())

        // Act
        val result = runBlocking { searchArticleRepositoryImpl.searchProduct(SEARCH_QUERY) }

        // Assert
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat((result as ApiResult.Error).errorWrapper).isInstanceOf(ErrorWrapper.UnknownError::class.java)
        assertThat((result.errorWrapper as ErrorWrapper.UnknownError).statusCode).isEqualTo(null)
        coVerify(exactly = 1) { searchProductDataSource.searchProduct(SEARCH_QUERY) }
    }


    @Test
    fun searchProduct_datasource_returns_ServiceNotAvailableError() = runTest {
        // Arrange
        val query = "some_query"
        val exception = IOException("Service is not available")
        coEvery { searchProductDataSource.searchProduct(query) } throws exception

        // Act
        val result = searchArticleRepositoryImpl.searchProduct(query)

        // Assert
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat((result as ApiResult.Error).errorWrapper).isInstanceOf(ErrorWrapper.ServiceNotAvailable::class.java)
        coVerify(exactly = 1) { searchProductDataSource.searchProduct(query) }
    }

    @Test
    fun searchProduct_datasource_returns_unknownError() = runTest {
        // Arrange
        coEvery { searchProductDataSource.searchProduct(SEARCH_QUERY) } returns
                Response.error(404, "error".toResponseBody())
        // Act
        val result = searchArticleRepositoryImpl.searchProduct(SEARCH_QUERY)
        // Assert
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat((result as ApiResult.Error).errorWrapper).isInstanceOf(ErrorWrapper.UnknownError::class.java)
        coVerify(exactly = 1) { searchProductDataSource.searchProduct(SEARCH_QUERY) }
    }

    @Test
    fun searchProduct_datasource_throws_exception_returns_ServiceNotAvailableError() = runTest {
        // Arrange
        coEvery { searchProductDataSource.searchProduct(SEARCH_QUERY) } throws RuntimeException()
        // Act
        val result = searchArticleRepositoryImpl.searchProduct(SEARCH_QUERY)
        // Assert
        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        assertThat((result as ApiResult.Error).errorWrapper).isInstanceOf(ErrorWrapper.UnknownError::class.java)
    }
}