package com.currenzy.data.repository

import com.currenzy.model.currency_converter.ExchangeRate
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    suspend fun getExchangeRate(): ExchangeRate
}