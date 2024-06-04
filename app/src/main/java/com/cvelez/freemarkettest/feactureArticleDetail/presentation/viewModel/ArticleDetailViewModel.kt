package com.cvelez.freemarkettest.feactureArticleDetail.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvelez.freemarkettest.core.network.wraps.ApiResult
import com.cvelez.freemarkettest.core.network.wraps.ErrorWrapper
import com.cvelez.freemarkettest.feactureArticleDetail.domain.ArticleDetailRepository
import com.cvelez.freemarkettest.feactureArticleDetail.presentation.estateUi.ArticleDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val productDetailRepository: ArticleDetailRepository
) : ViewModel() {

    var uiState by mutableStateOf(ArticleDetailUiState())
        private set

    init {
        loadProductDetail()
    }

    private fun loadProductDetail() {
        viewModelScope.launch {
            uiState = uiState.copy(loadingState = true)
            val id: String = savedStateHandle["productId"] ?: run {
                handleErrorResult(ErrorWrapper.UnknownError)
                return@launch
            }
            when (val result = productDetailRepository.getProductDetail(id)) {
                is ApiResult.Error -> handleErrorResult(result.error)
                is ApiResult.Success -> uiState = uiState.copy(
                    product = result.data,
                    loadingState = false
                )
            }
        }
    }

    private fun handleErrorResult(errorWrapper: ErrorWrapper?) {
        val message: String = when (errorWrapper) {
            ErrorWrapper.ServiceNotAvailable -> {
                "An error occurred while getting the results, check your internet connection."
            }
            is ErrorWrapper.ServiceInternalError -> {
                "It is not possible to obtain the results at this time."
            }
            ErrorWrapper.UnknownError, null -> {
                "An unexpected error occurred when obtaining the results."
            }
        }
        uiState = uiState.copy(
            errorState = true,
            errorMessage = message,
            loadingState = false
        )
    }
}