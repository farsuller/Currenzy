package com.currenzy.model.currency_converter

data class ExchangeRate(
    val baseCurrency: String,
    val rates : Map<String, Double>,
    val lastUpdatedDate: String
)
