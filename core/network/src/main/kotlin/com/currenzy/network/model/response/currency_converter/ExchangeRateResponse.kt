package com.currenzy.network.model.response.currency_converter

import com.currenzy.model.currency_converter.CurrencyInfo
import com.currenzy.model.currency_converter.ExchangeRate
import com.currenzy.model.currency_converter.Meta
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRateResponse (
    val meta : Meta,
    val data : Map<String, CurrencyInfo>
)

fun ExchangeRateResponse.toExternalModel(baseCurrency: String): ExchangeRate{
    return ExchangeRate(
        baseCurrency = baseCurrency,
        rates = this.data.mapValues { it.value.value },
        lastUpdatedDate = meta.lastUpdatedAt)
}