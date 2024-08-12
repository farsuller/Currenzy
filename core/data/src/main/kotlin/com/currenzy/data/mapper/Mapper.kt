package com.currenzy.data.mapper

import com.currenzy.database.model.ExchangeRatesEntity
import com.currenzy.network.model.response.currency_converter.ExchangeRateResponse

fun ExchangeRateResponse.toEntity(baseCurrencyCode: String): ExchangeRatesEntity {
    return ExchangeRatesEntity(
        baseCurrency = baseCurrencyCode,
        lastUpdatedDate = meta.lastUpdatedAt,
        exchangeRates = data
    )
}