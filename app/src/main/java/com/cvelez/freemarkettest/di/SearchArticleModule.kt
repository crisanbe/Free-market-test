package com.cvelez.freemarkettest.di

import com.cvelez.freemarkettest.core.network.api.ApiService
import com.cvelez.freemarkettest.featureSearch.data.repository.SearchArticleRepositoryImpl
import com.cvelez.freemarkettest.featureSearch.data.repository.dataSource.SearchArticleDataSource
import com.cvelez.freemarkettest.featureSearch.data.repository.dataSourceImpl.SearchArticleDataSourceImpl
import com.cvelez.freemarkettest.featureSearch.domain.SearchArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchArticleModule {

    @Singleton
    @Provides
    fun providesSearchArticleDataSource(apiService: ApiService) :SearchArticleDataSource {
        return SearchArticleDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun providesSearchArticleRepository(searchArticleDataSource: SearchArticleDataSource) : SearchArticleRepository {
        return SearchArticleRepositoryImpl(searchArticleDataSource)
    }
}