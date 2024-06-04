package com.cvelez.freemarkettest.featureSearch.data.model

import com.google.gson.annotations.SerializedName

data class ArticleAttributesValues(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("source") var source: Long? = null
)
