package com.cvelez.freemarkettest.di

import com.cvelez.freemarkettest.core.network.api.ApiService
import com.cvelez.freemarkettest.feactureArticleDetail.data.repository.ArticleDetailRepositoryImpl
import com.cvelez.freemarkettest.feactureArticleDetail.data.repository.dataSource.ArticleDetailDataSource
import com.cvelez.freemarkettest.feactureArticleDetail.data.repository.dataSourceImpl.ArticleDetailDataSourceImpl
import com.cvelez.freemarkettest.feactureArticleDetail.domain.ArticleDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ArticleDetailModule {
    @Singleton
    @Provides
    fun providesArticleDetailDataSource(apiService: ApiService): ArticleDetailDataSource {
        return ArticleDetailDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun providesArticleDetailRepository(productDetailDataSource: ArticleDetailDataSource) : ArticleDetailRepository {
        return ArticleDetailRepositoryImpl(productDetailDataSource)
    }
}