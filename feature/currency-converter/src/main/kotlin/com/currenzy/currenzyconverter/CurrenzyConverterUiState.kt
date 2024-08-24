package com.currenzy.currenzyconverter

/**
 * Represents the state of the Currenzy Converter UI.
 *
 * This data class holds the UI state for the currency converter screen, including information about the current
 * loading status, available currencies, and the conversion details.
 *
 * @property isLoading Indicates if the data is currently being loaded. Defaults to true.
 * @property allCurrencies A list of all available currencies for conversion. Defaults to an empty list.
 * @property fromCurrency The currency selected as the source for conversion. Defaults to an empty currency model.
 * @property toCurrency The currency selected as the target for conversion. Defaults to an empty currency model.
 * @property indicativeExchangeRate A string representing the exchange rate between the source and target currencies. Defaults to an empty string.
 * @property lastUpdated The date when the exchange rates were last updated. Defaults to an empty string.
 */
data class CurrenzyConverterUiState(
    val isLoading: Boolean = true,
    val allCurrencies: List<CurrenzyUiModel> = emptyList(),
    val fromCurrency: CurrenzyUiModel = CurrenzyUiModel("", ""),
    val toCurrency: CurrenzyUiModel = CurrenzyUiModel("", ""),
    val indicativeExchangeRate: String = "",
    val lastUpdated: String = ""
) {
    companion object {
        /**
         * Provides a preview state for the Currenzy Converter UI.
         *
         * This static instance can be used for previewing the UI in a static state where specific values are set
         * for demonstration or testing purposes.
         */
        val PreviewData = CurrenzyConverterUiState(
            fromCurrency = CurrenzyUiModel(code = "USD", value = "1000.00"),
            toCurrency = CurrenzyUiModel(code = "USD", value = "321.00"),
            indicativeExchangeRate = "1 USD = 1 USD"
        )
    }
}