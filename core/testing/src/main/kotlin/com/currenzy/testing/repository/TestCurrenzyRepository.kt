package com.currenzy.testing.repository

import Synchronizer
import com.currenzy.data.repository.CurrencyRepository
import com.currenzy.model.currency_converter.ExchangeRates
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

// A test implementation of CurrencyRepository for testing or development purposes
class TestCurrenzyRepository : CurrencyRepository {

    // MutableSharedFlow used to emit and observe exchange rates in a non-blocking manner
    private val exchangeRates: MutableSharedFlow<ExchangeRates> = MutableSharedFlow(
        replay = 1, // Keep the most recent emitted value to new subscribers
        onBufferOverflow = BufferOverflow.DROP_OLDEST // Drop the oldest value if buffer is full
    )

    // Provides a Flow to observe exchange rates
    override fun getExchangeRate(): Flow<ExchangeRates> {
        return exchangeRates
    }

    // Emit a new exchange rate value without suspending the coroutine
    // tryEmit will not suspend and returns false if the value could not be emitted (e.g., if the buffer is full)
    fun sendExchangeRate(exchangeRates: ExchangeRates) {
        this.exchangeRates.tryEmit(exchangeRates)
    }

    // A no-op implementation of syncWith for testing purposes
    override suspend fun syncWith(synchronizer: Synchronizer) {}
}