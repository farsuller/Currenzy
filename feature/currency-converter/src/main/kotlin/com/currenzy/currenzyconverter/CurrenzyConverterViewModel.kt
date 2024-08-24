package com.currenzy.currenzyconverter

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currenzy.data.repository.CurrencyRepository
import com.currenzy.data.worker.SyncManager
import com.currenzy.data.worker.WorkSyncManager
import com.currenzy.model.currency_converter.CurrencyInfo
import com.currenzy.model.currency_converter.ExchangeRates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.update
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CurrenzyConverterViewModel @Inject constructor(
    private val currenzyRepository: CurrencyRepository,
    private val sharedPreferences: SharedPreferences,
    workSyncManager: SyncManager
) : ViewModel() {

    // State flow to hold and observe UI state
    private val _uiState = MutableStateFlow(CurrenzyConverterUiState())
    val uiState = _uiState.asStateFlow()

    // Stores the latest exchange rates
    private lateinit var exchangeRates: ExchangeRates

    companion object {
        // Keys for storing currency codes in SharedPreferences
        const val FROM_CURRENCY_KEY = "fromCurrencyKey"
        const val TO_CURRENCY_KEY = "toCurrencyKey"
    }

    // Initialize ViewModel
    init {
        // Observe sync state to update loading status
        workSyncManager.isSyncing
            .onEach { isLoading ->
                _uiState.update {
                    it.copy(
                        isLoading = isLoading
                    )
                }
            }.launchIn(viewModelScope)

        // Initialize UI state
        initUiState()
    }

    // Initialize the UI state based on exchange rates
    private fun initUiState() {
        currenzyRepository
            .getExchangeRate()
            .retryWhen { cause, _ -> cause is IllegalStateException }
            .onEach { exchangeRates ->
                if (exchangeRates.rates.isEmpty()) {
                    return@onEach
                }
                this.exchangeRates = exchangeRates

                // Update UI state with the fetched exchange rates
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        allCurrencies = exchangeRates.rates.keys.map {
                            CurrenzyUiModel(code = it, "")
                        },
                        lastUpdated = fromDate(exchangeRates.lastUpdatedDate)
                    )
                }
                // Set initial currencies based on user preferences
                setInitialCurrencies()
            }.launchIn(viewModelScope)
    }

    // Set initial currencies based on user preferences and conversion rates
    private fun setInitialCurrencies() {
        val initialCurrencies = getUserCurrencies()

        val convertedResult = convert(
            fromVsBaseValue = initialCurrencies.first.value,
            toVsBaseValue = initialCurrencies.second.value,
            amount = 1.0
        )

        // Update UI state with initial currency settings
        _uiState.update {
            it.copy(
                fromCurrency = CurrenzyUiModel(initialCurrencies.first.code, "1.00"),
                toCurrency = CurrenzyUiModel(initialCurrencies.second.code, convertedResult),
                indicativeExchangeRate = "1 ${initialCurrencies.first.code} = $convertedResult ${initialCurrencies.second.code}"
            )
        }
    }

    // Get user-selected currencies from SharedPreferences
    private fun getUserCurrencies(): Pair<CurrencyInfo, CurrencyInfo> {
        val fromCurrencyCode = sharedPreferences.getString(FROM_CURRENCY_KEY, null)
        val toCurrencyCode = sharedPreferences.getString(TO_CURRENCY_KEY, null)
        var fromCurrency = CurrencyInfo("USD", exchangeRates.rates.getValue("USD"))
        var toCurrency = CurrencyInfo("PHP", exchangeRates.rates.getValue("PHP"))

        if (fromCurrencyCode != null) {
            fromCurrency = CurrencyInfo(fromCurrencyCode, exchangeRates.rates.getValue(fromCurrencyCode))
        }
        if (toCurrencyCode != null) {
            toCurrency = CurrencyInfo(toCurrencyCode, exchangeRates.rates.getValue(toCurrencyCode))
        }
        return Pair(fromCurrency, toCurrency)
    }

    // Handle changes in the 'from' currency field
    fun onFromCurrencyChange(fromCurrency: CurrenzyUiModel) {
        when (val validResult = fromCurrency.value.safeToDouble()) {
            is StringToDoubleConversionResult.Valid -> {
                with(uiState.value) {
                    val convertedResult = convert(
                        fromVsBaseValue = exchangeRates.rates.getValue(fromCurrency.code),
                        toVsBaseValue = exchangeRates.rates.getValue(toCurrency.code),
                        amount = validResult.value
                    )

                    // Update UI state with new 'from' currency and converted 'to' currency value
                    _uiState.update {
                        it.copy(
                            fromCurrency = fromCurrency,
                            toCurrency = toCurrency.copy(value = convertedResult),
                            indicativeExchangeRate = "1 ${fromCurrency.code} = ${
                                getIndicativeExchangeRate(
                                    fromCurrencyCode = fromCurrency.code,
                                    toCurrencyCode = toCurrency.code
                                )
                            } ${toCurrency.code}"
                        )
                    }
                }
                // Save selected 'from' currency to SharedPreferences
                sharedPreferences.edit().putString(FROM_CURRENCY_KEY, fromCurrency.code).apply()
            }

            is StringToDoubleConversionResult.Empty -> {
                // Clear 'from' and 'to' currency values if input is empty
                _uiState.update {
                    it.copy(
                        fromCurrency = it.fromCurrency.copy(value = ""),
                        toCurrency = it.toCurrency.copy(value = "")
                    )
                }
            }

            is StringToDoubleConversionResult.Invalid -> Unit
        }
    }

    // Handle changes in the 'to' currency field
    fun onToCurrencyChange(toCurrency: CurrenzyUiModel) {

        if (toCurrency.code != uiState.value.toCurrency.code) {
            // Update 'to' currency and recalculate 'from' currency
            _uiState.update {
                it.copy(
                    toCurrency = toCurrency
                )
            }
            onFromCurrencyChange(fromCurrency = uiState.value.fromCurrency)
            // Save selected 'to' currency to SharedPreferences
            sharedPreferences.edit().putString(TO_CURRENCY_KEY, toCurrency.code).apply()
            return
        }

        when (val validResult = toCurrency.value.safeToDouble()) {
            is StringToDoubleConversionResult.Valid -> {
                with(uiState.value) {
                    val convertedResult = convert(
                        fromVsBaseValue = exchangeRates.rates.getValue(toCurrency.code),
                        toVsBaseValue = exchangeRates.rates.getValue(fromCurrency.code),
                        amount = validResult.value
                    )

                    // Update UI state with new 'to' currency and recalculated 'from' currency value
                    _uiState.update {
                        it.copy(
                            fromCurrency = fromCurrency.copy(value = convertedResult),
                            toCurrency = toCurrency,
                        )
                    }
                }
            }

            is StringToDoubleConversionResult.Empty -> {
                // Clear 'from' and 'to' currency values if input is empty
                _uiState.update {
                    it.copy(
                        fromCurrency = it.fromCurrency.copy(value = ""),
                        toCurrency = it.toCurrency.copy(value = "")
                    )
                }
            }

            is StringToDoubleConversionResult.Invalid -> Unit
        }
    }

    // Swap 'from' and 'to' currencies
    fun swapCurrencies() {
        _uiState.update {
            it.copy(
                fromCurrency = it.toCurrency,
                toCurrency = it.fromCurrency
            )
        }
    }

    // Convert a string value to a Double, handling empty and invalid cases
    private fun String.safeToDouble(): StringToDoubleConversionResult {
        return if (this.endsWith(".")) {
            StringToDoubleConversionResult.Valid(dropLast(1).toDouble())
        } else if (isEmpty()) {
            StringToDoubleConversionResult.Empty
        } else {
            this.toDoubleOrNull()?.let {
                StringToDoubleConversionResult.Valid(it)
            } ?: StringToDoubleConversionResult.Invalid
        }
    }

    // Get the indicative exchange rate between two currencies
    private fun getIndicativeExchangeRate(
        fromCurrencyCode: String,
        toCurrencyCode: String
    ): String {
        return convert(
            fromVsBaseValue = exchangeRates.rates.getValue(fromCurrencyCode),
            toVsBaseValue = exchangeRates.rates.getValue(toCurrencyCode),
            amount = 1.0
        )
    }

    // Convert an amount from one currency to another
    private fun convert(
        fromVsBaseValue: Double,
        toVsBaseValue: Double,
        amount: Double
    ): String {
        return CurrenzyConverter.convert(
            fromCurrencyRateVsBaseCurrencyRate = fromVsBaseValue,
            toCurrencyRateVsBaseCurrencyRate = toVsBaseValue,
            amount = amount
        ).let {
            String.format(Locale.ENGLISH, "%2f", it)
        }
    }

    // Format the date string
    private fun fromDate(date: String): String {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            OffsetDateTime.parse(date).format(formatter)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}

// Sealed interface to represent conversion result states
private sealed interface StringToDoubleConversionResult {
    data class Valid(val value: Double) : StringToDoubleConversionResult

    data object Invalid : StringToDoubleConversionResult

    data object Empty : StringToDoubleConversionResult
}