package com.currenzy.currenzyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currenzy.data.repository.CurrencyRepository
import com.currenzy.model.currency_converter.ExchangeRate
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrenzyConverterViewModel @Inject constructor(
    private val currenzyRepository: CurrencyRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(CurrenzyConverterUiState())
    val uiState = _uiState.asStateFlow()

    private lateinit var exchangeRates: ExchangeRate
    init {

    }

    private fun initUiState(){

        val handler = CoroutineExceptionHandler{ coroutineContext, throwable ->
        }

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
    }

    private fun getUserCurrencies(){
        
    }
}