package com.cvelez.freemarkettest.featureSearch.data.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("id"                  ) var id                 : String?               = null,
    @SerializedName("title"               ) var title              : String?               = null,
    @SerializedName("condition"           ) var condition          : String?               = null,
    @SerializedName("thumbnail_id"        ) var thumbnailId        : String?               = null,
    @SerializedName("catalog_product_id"  ) var catalogProductId   : String?               = null,
    @SerializedName("listing_type_id"     ) var listingTypeId      : String?               = null,
    @SerializedName("permalink"           ) var permalink          : String?               = null,
    @SerializedName("buying_mode"         ) var buyingMode         : String?               = null,
    @SerializedName("site_id"             ) var siteId             : String?               = null,
    @SerializedName("category_id"         ) var categoryId         : String?               = null,
    @SerializedName("domain_id"           ) var domainId           : String?               = null,
    @SerializedName("variation_id"        ) var variationId        : String?               = null,
    @SerializedName("thumbnail"           ) var thumbnail          : String?               = null,
    @SerializedName("currency_id"         ) var currencyId         : String?               = null,
    @SerializedName("order_backend"       ) var orderBackend       : Int?                  = null,
    @SerializedName("price"               ) var price              : Long?                  = null,
    @SerializedName("original_price"      ) var originalPrice      : String?               = null,
    @SerializedName("sale_price"          ) var salePrice          : String?               = null,
    @SerializedName("available_quantity"  ) var availableQuantity  : Long?                  = null,
    @SerializedName("official_store_id"   ) var officialStoreId    : String?               = null,
    @SerializedName("use_thumbnail_id"    ) var useThumbnailId     : Boolean?              = null,
    @SerializedName("accepts_mercadopago" ) var acceptsMercadopago : Boolean?              = null,
    @SerializedName("variation_filters"   ) var variationFilters   : List<String>     = listOf<String>(),
    @SerializedName("stop_time"           ) var stopTime           : String?               = null,
    @SerializedName("seller"              ) var seller             : Seller?               = Seller(),
    @SerializedName("attributes"          ) var attributes         : List<ArticleAttributes> = listOf<ArticleAttributes>(),
    @SerializedName("winner_item_id"      ) var winnerItemId       : String?               = null,
    @SerializedName("catalog_listing"     ) var catalogListing     : Boolean?              = null,
    @SerializedName("discounts"           ) var discounts          : String?               = null,
    @SerializedName("promotions"          ) var promotions         : List<String>     = listOf<String>(),
    @SerializedName("inventory_id"        ) var inventoryId        : String?               = null
)
