package com.cvelez.freemarkettest.featureSearch.data.model

import com.google.gson.annotations.SerializedName

data class Seller(
    @SerializedName("id"       ) var id       : Long?    = null,
    @SerializedName("nickname" ) var nickname : String? = null
)
