package com.cvelez.freemarkettest.featureSearch.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cvelez.freemarkettest.featureSearch.presentation.estateUi.SearchArticleEvents
import com.cvelez.freemarkettest.featureSearch.presentation.viewModel.SearchArticleViewModel

@Composable
fun SearchArticleRoute(viewModel: SearchArticleViewModel,modifier: Modifier, onShowProductDetail : (String?) -> Unit) {
    Column {
        SearchArticleScreen(uiState = viewModel.uiState, onQuerySearch = {
            viewModel.onEvent(SearchArticleEvents.OnQueryChange(it))
        }, onSearchProduct = {
            viewModel.onEvent(SearchArticleEvents.OnClickSearchProduct)
        }, onProductClick = onShowProductDetail, modifier = modifier)
    }
}