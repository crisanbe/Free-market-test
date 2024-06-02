package com.cvelez.freemarkettest.feactureArticleDetail.data.repository.dataSource

import com.cvelez.freemarkettest.feactureArticleDetail.data.model.ArticleDetail
import retrofit2.Response

fun interface ArticleDetailDataSource {
    suspend fun getProductDetail(productId:String) : Response<ArticleDetail?>
}