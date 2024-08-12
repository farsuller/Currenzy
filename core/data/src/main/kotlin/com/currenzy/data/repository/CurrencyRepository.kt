package com.currenzy.data.repository

import Syncable
import com.currenzy.model.currency_converter.ExchangeRates
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository : Syncable {
    fun getExchangeRate(): Flow<ExchangeRates>
}