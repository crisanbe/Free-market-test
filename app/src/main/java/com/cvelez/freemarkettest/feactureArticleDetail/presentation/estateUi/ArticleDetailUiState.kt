package com.cvelez.freemarkettest.feactureArticleDetail.presentation.estateUi

import com.cvelez.freemarkettest.feactureArticleDetail.data.model.ArticleDetail

data class ArticleDetailUiState(
    val loadingState: Boolean = false,
    val product: ArticleDetail? = ArticleDetail(),
    val errorState : Boolean = false,
    val errorMessage : String? = null
)