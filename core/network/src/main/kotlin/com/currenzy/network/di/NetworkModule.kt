package com.currenzy.network.di

import com.currenzy.network.BuildConfig
import com.currenzy.network.interceptor.HeadersInterceptor
import com.currenzy.network.service.CurrencyService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Dagger module for providing network-related dependencies.
 *
 * This module is responsible for setting up and providing the necessary components for network communication,
 * including the HTTP client and Retrofit service.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides a singleton instance of [OkHttpClient].
     *
     * Configures the [OkHttpClient] with a logging interceptor and a custom headers interceptor.
     * The logging interceptor is used to log HTTP request and response details at the BASIC level.
     * The headers interceptor is used to add custom headers to network requests, including an API key.
     *
     * @return A singleton instance of [OkHttpClient].
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        // Create a logging interceptor with BASIC level
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
            // Add the logging interceptor to log HTTP request and response details
            .addNetworkInterceptor(loggingInterceptor)
            // Add a custom interceptor to attach API key to headers
            .addNetworkInterceptor(HeadersInterceptor(mapOf("apiKey" to BuildConfig.API_KEY)))
            .build()
    }

    /**
     * Provides a singleton instance of [CurrencyService] using Retrofit.
     *
     * Configures Retrofit with a base URL and the [OkHttpClient] provided by [provideOkHttpClient].
     * Uses JSON converter factory to handle JSON data format.
     * Creates an implementation of [CurrencyService] interface for making network requests.
     *
     * @param client The [OkHttpClient] to be used by Retrofit for network operations.
     * @return A singleton instance of [CurrencyService].
     */
    @Provides
    @Singleton
    fun provideCurrencyRetrofit(client: OkHttpClient): CurrencyService {
        return Retrofit.Builder()
            // Set the base URL for Retrofit
            .baseUrl(BuildConfig.BASE_URL)
            // Set the OkHttpClient for Retrofit
            .client(client)
            // Add JSON converter factory for handling JSON responses
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            // Create an implementation of the CurrencyService interface
            .create(CurrencyService::class.java)
    }
}