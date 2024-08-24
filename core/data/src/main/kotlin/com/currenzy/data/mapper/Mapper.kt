package com.currenzy.data.mapper

import com.currenzy.database.model.ExchangeRatesEntity
import com.currenzy.network.model.response.currency_converter.ExchangeRateResponse

/**
 * Extension function to convert an [ExchangeRateResponse] to an [ExchangeRatesEntity].
 *
 * @param baseCurrencyCode The base currency code used for the exchange rates.
 * @return An [ExchangeRatesEntity] representing the exchange rates data.
 */
fun ExchangeRateResponse.toEntity(baseCurrencyCode: String): ExchangeRatesEntity {
    return ExchangeRatesEntity(
        // Set the base currency for the exchange rates
        baseCurrency = baseCurrencyCode,

        // Use the last updated timestamp from the meta information
        lastUpdatedDate = meta.lastUpdatedAt,

        // Map the data to the exchange rates, which should be in the format expected by the entity
        exchangeRates = data
    )
}