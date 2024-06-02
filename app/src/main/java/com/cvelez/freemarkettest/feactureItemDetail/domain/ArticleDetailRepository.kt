package com.cvelez.freemarkettest.feactureItemDetail.domain

import com.cvelez.freemarkettest.core.network.wraps.ApiResult
import com.cvelez.freemarkettest.feactureItemDetail.data.model.ArticleDetail

fun interface ArticleDetailRepository {
    suspend fun getProductDetail(productId:String) : ApiResult<ArticleDetail?>
}