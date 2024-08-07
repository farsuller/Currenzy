package com.currenzy.currenzyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currenzy.data.repository.CurrencyRepository
import com.currenzy.model.currency_converter.CurrencyInfo
import com.currenzy.model.currency_converter.ExchangeRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CurrenzyConverterViewModel @Inject constructor(
    private val currenzyRepository: CurrencyRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(CurrenzyConverterUiState())
    val uiState = _uiState.asStateFlow()

    private lateinit var exchangeRates: ExchangeRate

    init {
        initUiState()
    }

    private fun initUiState(){
        viewModelScope.launch {
            exchangeRates = currenzyRepository.getExchangeRate()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    allCurrencies = exchangeRates.rates.keys.map {
                        CurrenzyUiModel(code = it, "")
                    },
                    lastUpdated = exchangeRates.lastUpdatedDate
                )
            }

            setInitialCurrencies()
        }
    }

    private fun setInitialCurrencies(){
        val initialCurrencies = getUserCurrencies()

        val convertedResult = convert(
            fromVsBaseValue = initialCurrencies.first.value,
            toVsBaseValue = initialCurrencies.second.value,
            amount = 1.0
        )

        _uiState.update {
            it.copy(
                fromCurrency = CurrenzyUiModel(initialCurrencies.first.code, "1.00"),
                toCurrency = CurrenzyUiModel(initialCurrencies.second.code, convertedResult),
                indicativeExchangeRate = "1 ${initialCurrencies.first.code} = $convertedResult ${initialCurrencies.second.code}"
            )
        }

    }

    private fun getUserCurrencies() : Pair<CurrencyInfo, CurrencyInfo>{
        val fromCurrency = CurrencyInfo("USD", exchangeRates.rates.getValue("USD"))
        val toCurrency = CurrencyInfo("EUR", exchangeRates.rates.getValue("EUR"))

        return Pair(fromCurrency,toCurrency)
    }

    fun onFromCurrencyChange(fromCurrency: CurrenzyUiModel){
        when(val validResult = fromCurrency.value.safeToDouble()){
            is StringToDoubleConversionResult.Valid -> {
                with(uiState.value){
                    val convertedResult = convert(
                        fromVsBaseValue = exchangeRates.rates.getValue(fromCurrency.code),
                        toVsBaseValue = exchangeRates.rates.getValue(toCurrency.code),
                        amount = validResult.value
                    )

                    _uiState.update {
                        it.copy(
                            fromCurrency = fromCurrency,
                            toCurrency = toCurrency.copy(value = convertedResult),
                            indicativeExchangeRate = "1 ${fromCurrency.code} = ${getIndicativeExchangeRate(
                                fromCurrencyCode = fromCurrency.code, 
                                toCurrencyCode = toCurrency.code)} ${toCurrency.code}"
                        )
                    }
                }
            }
            is StringToDoubleConversionResult.Empty ->{
                _uiState.update {
                    it.copy(
                        fromCurrency = it.fromCurrency.copy(value = "0.00"),
                        toCurrency = it.toCurrency.copy("0.00")
                    )
                }
            }
            is StringToDoubleConversionResult.Invalid -> Unit
        }
    }

    private fun String.safeToDouble() : StringToDoubleConversionResult{
        return if (this.endsWith(".")){
            StringToDoubleConversionResult.Valid(dropLast(1).toDouble())
        }
        else if(isEmpty()){
            StringToDoubleConversionResult.Empty
        }
        else {
            this.toDoubleOrNull()?.let {
                StringToDoubleConversionResult.Valid(it)
            } ?: StringToDoubleConversionResult.Invalid
        }
    }

    private fun getIndicativeExchangeRate(
        fromCurrencyCode : String,
        toCurrencyCode : String
    ) : String{
        return convert(
            fromVsBaseValue = exchangeRates.rates.getValue(fromCurrencyCode),
            toVsBaseValue = exchangeRates.rates.getValue(toCurrencyCode),
            amount = 1.0
        )
    }

    private fun convert(fromVsBaseValue : Double,
                        toVsBaseValue : Double,
                        amount: Double) : String {
        return CurrenzyConverter.convert(
            fromCurrencyRateVsBaseCurrencyRate = fromVsBaseValue,
            toCurrencyRateVsBaseCurrencyRate = toVsBaseValue,
            amount = amount
        ).let {
            String.format(Locale.ENGLISH, "%2f", it)
        }
    }
}


private sealed interface StringToDoubleConversionResult{
    data class Valid(val value : Double) : StringToDoubleConversionResult

    data object Invalid : StringToDoubleConversionResult

    data object Empty : StringToDoubleConversionResult
}