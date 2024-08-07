package com.currenzy.currenzyconverter

data class CurrenzyConverterUiState(
    val isLoading: Boolean = true,
    val allCurrencies : List<CurrenzyUiModel> = emptyList(),
    val fromCurrency : CurrenzyUiModel = CurrenzyUiModel("", ""),
    val toCurrency : CurrenzyUiModel = CurrenzyUiModel("", ""),
    val indicativeExchangeRate : String = "",
    val lastUpdated : String = ""
)