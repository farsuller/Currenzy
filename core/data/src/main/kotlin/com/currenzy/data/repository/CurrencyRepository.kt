package com.currenzy.data.repository

import Syncable
import com.currenzy.model.currency_converter.ExchangeRates
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository : Syncable {
    /**
     * Returns a [Flow] of [ExchangeRates] representing the current exchange rates data.
     *
     * The [Flow] allows observing and reacting to changes in exchange rates over time.
     * It is typically used in a reactive programming model where updates to the exchange rates are automatically propagated to the consumer.
     *
     * @return A [Flow] of [ExchangeRates].
     */
    fun getExchangeRate(): Flow<ExchangeRates>
}