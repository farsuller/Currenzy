package com.currenzy.testing.repository

import Synchronizer
import com.currenzy.data.repository.CurrencyRepository
import com.currenzy.model.currency_converter.ExchangeRates
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestCurrenzyRepository : CurrencyRepository {

    private val exchangeRates : MutableSharedFlow<ExchangeRates> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getExchangeRate(): Flow<ExchangeRates> {
        return exchangeRates
    }

    // tryEmit will emit value without suspending coroutine
    fun sendExchangeRate(exchangeRates: ExchangeRates) {
        this.exchangeRates.tryEmit(exchangeRates)
    }
    override suspend fun syncWith(synchronizer: Synchronizer) {}
}