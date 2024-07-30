package com.currenzy.network.service

import com.currenzy.network.model.response.currency_converter.ExchangeRateResponse
import retrofit2.http.GET

interface CurrencyService {

    @GET("latest")
    suspend fun getExchangeRates(): ExchangeRateResponse
}