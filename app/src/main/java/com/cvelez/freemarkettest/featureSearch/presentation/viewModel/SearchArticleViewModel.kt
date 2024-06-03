package com.cvelez.freemarkettest.featureSearch.presentation.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvelez.freemarkettest.core.network.wraps.ApiResult
import com.cvelez.freemarkettest.core.network.wraps.ErrorWrapper
import com.cvelez.freemarkettest.featureSearch.data.model.Article
import com.cvelez.freemarkettest.featureSearch.domain.SearchArticleRepository
import com.cvelez.freemarkettest.featureSearch.presentation.estateUi.SearchArticleEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchArticleViewModel @Inject constructor(private val searchRepository: SearchArticleRepository) :
    ViewModel() {
    var uiState by mutableStateOf(SearchItemUiState())
        private set


    init {
        onEvent(SearchArticleEvents.OnQueryChange("computadora"))
        viewModelScope.launch {
            searchProducts()
            uiState = uiState.copy(loadingState = true)
            // Simulate a delay for the loading state
            kotlinx.coroutines.delay(2000)
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
            when(val result = searchRepository.searchProduct(uiState.searchQuery)){
                is ApiResult.Error -> {
                    handleErrorResult(result.errorWrapper)
                }
                is ApiResult.Success -> {
                    uiState = uiState.copy(loadingState = false, productList = result.data?.results ?: listOf())
                }
            }

        }

    }

    private fun handleErrorResult(errorWrapper: ErrorWrapper?) {
        val message :String = when(errorWrapper){
            ErrorWrapper.ServiceNotAvailable -> {
                "Ocurrio un error al obtener los resultados, revisa tu conexión a internet"
            }
            is ErrorWrapper.ServiceInternalError -> {
                "ahora mismo no es posible obtener los resultados"
            }
            ErrorWrapper.UnknownError -> {
                "Ocurrio un error inesperado al obtener los resultados"
            }
            else -> {
                "Ocurrio un error inesperado al obtener los resultados"
            }
        }
        uiState = uiState.copy(loadingState = false, errorState = true, errorMessage = message)
    }

}

data class SearchItemUiState(
    val searchQuery: String = "",
    val loadingState: Boolean = false,
    val productList: List<Article> = listOf(),
    val errorState : Boolean = false,
    val errorMessage : String? = null
)