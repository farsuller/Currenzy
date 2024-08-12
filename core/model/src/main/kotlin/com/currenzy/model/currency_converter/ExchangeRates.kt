package com.currenzy.model.currency_converter

data class ExchangeRates(
    val baseCurrency: String,
    val rates : Map<String, Double>,
    val lastUpdatedDate: String
)
