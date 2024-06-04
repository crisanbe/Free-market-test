package com.cvelez.freemarkettest.featureSearch.data.model

import com.google.gson.annotations.SerializedName

data class SearchArticleResult(
    @SerializedName("query") var query: String? = null,
    @SerializedName("results") var results: ArrayList<Article> = arrayListOf(),
)
