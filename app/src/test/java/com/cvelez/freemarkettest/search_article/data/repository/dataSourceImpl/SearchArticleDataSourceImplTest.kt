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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

class SearchArticleDataSourceImplTest {

    private lateinit var SUT: SearchArticleDataSourceImpl
    private lateinit var apiService: ApiService

    companion object {
        val TEST_QUERY = "tesing"
        val resultList = arrayListOf<Article>(
            Article(id = "1", title = "Producto 1", thumbnail = "priebas")
        )
        val searchResult = SearchArticleResult(query = TEST_QUERY, results = resultList)

        val EMPTY_QUERY = ""
        val emptyResultList = arrayListOf<Article>()
        val emptySearchResult = SearchArticleResult(query = EMPTY_QUERY, results = emptyResultList)
        val serverError : Response<SearchArticleResult?> = Response.error(500, ResponseBody.create(
            "application/json; charset=utf-8".toMediaType(),""))

    }

    @Before
    fun setUp() {
        apiService = mock { }
        SUT = SearchArticleDataSourceImpl(apiService)
    }

    @Test
    fun searchProduct_returns_correct_result() {
        runBlocking {
            //Arrange
            getCorrectResultApiservice()
            //Act
            val result = SUT.searchArticle(TEST_QUERY)
            //Assert
            assertThat(result.isSuccessful).isEqualTo(true)
            assertThat(result.body()).isEqualTo(searchResult)
            verify(1) { apiService.searchItems(TEST_QUERY) }
        }
    }

    @Test
    fun searchProduct_empty_query_returns_empty_result() {
        runBlocking {
            //Arrange
            getEmptyCorrectResultApiservice()
            //Act
            val result = SUT.searchArticle(EMPTY_QUERY)
            //Assert
            assertThat(result.isSuccessful).isEqualTo(true)
            assertThat(result.body()).isEqualTo(emptySearchResult)
            verify(1) { apiService.searchItems(TEST_QUERY) }
        }
    }

    @Test
    fun searchProduct_returns_error() {
        runBlocking {
            //Arrange
            getErrorResultApiservice()
            //Act
            val result = SUT.searchArticle(TEST_QUERY)
            //Assert
            assertThat(result.isSuccessful).isEqualTo(false)
            verify(1) { apiService.searchItems(TEST_QUERY) }
        }
    }

    private suspend fun getErrorResultApiservice() {
        whenever(apiService.searchItems(TEST_QUERY)).thenReturn(serverError)
    }

    private suspend fun getEmptyCorrectResultApiservice() {
        whenever(apiService.searchItems(EMPTY_QUERY)).thenReturn(
            Response.success(
                emptySearchResult
            )
        )
    }

    private suspend fun getCorrectResultApiservice() {
        whenever(apiService.searchItems(TEST_QUERY)).thenReturn(Response.success(searchResult))
    }
}