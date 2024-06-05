package com.cvelez.freemarkettest.featureSearch.presentation.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvelez.freemarkettest.core.network.wraps.ApiResult
import com.cvelez.freemarkettest.core.network.wraps.BugWrapper
import com.cvelez.freemarkettest.featureSearch.domain.SearchArticleRepository
import com.cvelez.freemarkettest.featureSearch.presentation.estateUi.SearchArticleEvents
import com.cvelez.freemarkettest.featureSearch.presentation.estateUi.SearchItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchArticleViewModel @Inject constructor(
    private val searchRepository: SearchArticleRepository
) : ViewModel() {

    var uiState by mutableStateOf(SearchItemUiState())
        private set

    init {
        onEvent(SearchArticleEvents.OnQueryChange("computadora"))
        performInitialSearch()
    }

    private fun performInitialSearch() {
        viewModelScope.launch {
            uiState = uiState.copy(loadingState = true)
            searchProducts()
            kotlinx.coroutines.delay(2000) // Simulate a delay for the loading state
            uiState = uiState.copy(loadingState = false)
        }
    }

    fun onEvent(events: SearchArticleEvents) {
        when (events) {
            is SearchArticleEvents.OnQueryChange -> {
                uiState = uiState.copy(searchQuery = events.query)
            }
            SearchArticleEvents.OnClickSearchProduct -> {
                Log.d("sat_tag", "onEvent: buscar ${uiState.searchQuery}")
                searchProducts()
            }
        }
    }

    private fun searchProducts() {
        if (uiState.searchQuery.isEmpty()) return
        uiState = uiState.copy(loadingState = true)
        viewModelScope.launch {
            when (val result = searchRepository.searchArticle(uiState.searchQuery)) {
                is ApiResult.Error -> handleErrorResult(result.errorWrapper)
                is ApiResult.Success -> uiState = uiState.copy(loadingState = false, productList = result.data?.results ?: listOf())
            }
        }
    }

    private fun handleErrorResult(errorWrapper: BugWrapper?) {
        val message: String = when (errorWrapper) {
            BugWrapper.ServiceNotAvailable -> "An error occurred while getting the results, check your internet connection.."
            is BugWrapper.ServiceInternalError -> "It is not possible to obtain the results at this time"
            BugWrapper.UnknownError -> "An unexpected error occurred when obtaining the results."
            else -> "An unexpected error occurred when obtaining the results."
        }
        uiState = uiState.copy(loadingState = false, errorState = true, errorMessage = message)
    }
}
