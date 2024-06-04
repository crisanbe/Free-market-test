package com.cvelez.freemarkettest.feactureArticleDetail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.cvelez.freemarkettest.feactureArticleDetail.presentation.viewModel.ArticleDetailViewModel

@Composable
fun ArticleDetailRoute(viewModel: ArticleDetailViewModel, onBackClicked : () -> Unit) {
    Column {
        ArticleDetailScreen(uiState = viewModel.uiState, onBackClicked = onBackClicked, onBuyClicked = {}, onAddToCartClicked = {})
    }
}