package com.cvelez.freemarkettest.featureSearch.data.model

import com.google.gson.annotations.SerializedName

data class ArticleAttributes(
    @SerializedName("id"                   ) var id                 : String?           = null,
    @SerializedName("name"                 ) var name               : String?           = null,
    @SerializedName("value_id"             ) var valueId            : String?           = null,
    @SerializedName("value_name"           ) var valueName          : String?           = null,
    @SerializedName("attribute_group_id"   ) var attributeGroupId   : String?           = null,
    @SerializedName("attribute_group_name" ) var attributeGroupName : String?           = null,
    @SerializedName("values"               ) var values             : List<ArticleAttributesValues> = arrayListOf(),
    @SerializedName("source"               ) var source             : Long?              = null,
    @SerializedName("value_type"           ) var valueType          : String?           = null
)
