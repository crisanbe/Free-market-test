package com.cvelez.freemarkettest.feactureItemDetail.data.repository.dataSource

import com.cvelez.freemarkettest.feactureItemDetail.data.model.ArticleDetail
import retrofit2.Response

fun interface ArticleDetailDataSource {
    suspend fun getProductDetail(productId:String) : Response<ArticleDetail?>
}