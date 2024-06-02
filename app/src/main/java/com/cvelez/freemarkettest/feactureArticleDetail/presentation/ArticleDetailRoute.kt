package com.cvelez.freemarkettest.feactureArticleDetail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun ArticleDetailRoute(viewModel: ArticleDetailViewModel, onBackClicked : () -> Unit) {
    Column {
        ArticleDetailScreen(uiState = viewModel.uiState, onBackClicked = onBackClicked)
    }
}