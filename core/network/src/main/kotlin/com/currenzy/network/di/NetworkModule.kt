package com.currenzy.network.di

import com.currenzy.network.interceptor.HeadersInterceptor
import com.currenzy.network.service.CurrencyService
import com.currenzy.network.util.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient():OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addNetworkInterceptor(HeadersInterceptor(mapOf("apiKey" to "cur_live_ZUIUCRQyFy3tc7WtxuF1XMN67fSPGAnk1Q90d53R")))
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyRetrofit(client: OkHttpClient) : CurrencyService{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(CurrencyService::class.java)
    }
}