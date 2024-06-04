package com.cvelez.freemarkettest.featureSearch.presentation.estateUi

import com.cvelez.freemarkettest.featureSearch.data.model.Article

data class SearchItemUiState(
    val searchQuery: String = "",
    val loadingState: Boolean = false,
    val productList: List<Article> = listOf(),
    val errorState : Boolean = false,
    val errorMessage : String? = null
)