package com.cvelez.freemarkettest.feactureArticleDetail.domain

import com.cvelez.freemarkettest.core.network.wraps.ApiResult
import com.cvelez.freemarkettest.feactureArticleDetail.data.model.ArticleDetail

fun interface ArticleDetailRepository {
    suspend fun getProductDetail(productId:String) : ApiResult<ArticleDetail?>
}