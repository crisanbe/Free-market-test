package com.cvelez.freemarkettest.feactureArticleDetail.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvelez.freemarkettest.core.network.wraps.ApiResult
import com.cvelez.freemarkettest.core.network.wraps.ErrorWrapper
import com.cvelez.freemarkettest.feactureArticleDetail.data.model.ArticleDetail
import com.cvelez.freemarkettest.feactureArticleDetail.domain.ArticleDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle,
                                                 private val productDetailRepository: ArticleDetailRepository) : ViewModel() {

    var uiState by mutableStateOf(
            ProductDetailUiState()
    )
        private set

    init {
        viewModelScope.launch {
            uiState =uiState.copy(loadingState = true)
            val id: String = requireNotNull(savedStateHandle["productId"])
            val result = productDetailRepository.getProductDetail(productId = id)
            when(result){
                is ApiResult.Error -> handleErrorResult(result.errorWrapper)
                is ApiResult.Success -> uiState = uiState.copy(product = result.data, loadingState = false)
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
        uiState = uiState.copy(errorState = true, errorMessage = message, loadingState = false)
    }


}

data class ProductDetailUiState(
    val loadingState: Boolean = false,
    val product: ArticleDetail? = ArticleDetail(),
    val errorState : Boolean = false,
    val errorMessage : String? = null
)