package com.currenzy.data.repository

import com.currenzy.model.currency_converter.ExchangeRate
import com.currenzy.network.model.response.currency_converter.toExternalModel
import com.currenzy.network.service.CurrencyService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val currencyService: CurrencyService) : CurrencyRepository {
    override suspend fun getExchangeRate(): ExchangeRate {
       return currencyService.getExchangeRates().toExternalModel(baseCurrency = "USD")
    }
}