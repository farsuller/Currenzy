package com.currenzy.currenzyconverter

data class CurrenzyConverterUiState(
    val isLoading: Boolean = true,
    val allCurrencies : List<CurrenzyUiModel> = emptyList(),
    val fromCurrency : CurrenzyUiModel = CurrenzyUiModel("", ""),
    val toCurrency : CurrenzyUiModel = CurrenzyUiModel("", ""),
    val indicativeExchangeRate : String = "",
    val lastUpdated : String = ""
){
    companion object {
        val PreviewData = CurrenzyConverterUiState(
            fromCurrency = CurrenzyUiModel(code = "USD", value = "1000.00"),
            toCurrency = CurrenzyUiModel(code = "USD", value = "321.00"),
            indicativeExchangeRate = "1 USD = 1 USD"
        )
    }
}