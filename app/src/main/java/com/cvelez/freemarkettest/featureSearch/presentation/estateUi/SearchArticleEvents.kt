package com.cvelez.freemarkettest.featureSearch.presentation.estateUi

sealed interface SearchArticleEvents {
    data class OnQueryChange(val query : String) : SearchArticleEvents

    object OnClickSearchProduct : SearchArticleEvents
}