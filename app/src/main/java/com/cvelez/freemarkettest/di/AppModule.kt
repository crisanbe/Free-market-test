package com.cvelez.freemarkettest.di

import com.cvelez.freemarkettest.Constants
import com.cvelez.freemarkettest.core.network.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providesRetrofitClient() = OkHttpClient.Builder()
        .connectTimeout(300, TimeUnit.SECONDS)
        .readTimeout(300, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun providesRetrofit(retrofitClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(retrofitClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)
}