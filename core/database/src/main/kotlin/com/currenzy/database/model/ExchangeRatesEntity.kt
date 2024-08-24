package com.currenzy.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currenzy.model.currency_converter.CurrencyInfo
import com.currenzy.model.currency_converter.ExchangeRates

// Entity class representing a table in the Room database
@Entity
data class ExchangeRatesEntity(
    // Primary key for the table, typically used for identifying unique records
    @PrimaryKey
    val lastUpdatedDate: String, // Represents the date when the exchange rates were last updated

    // Base currency code for the exchange rates
    val baseCurrency: String,

    // A map of exchange rates where the key is the currency code and the value is a CurrencyInfo object
    val exchangeRates: Map<String, CurrencyInfo>
)

// Extension function to convert ExchangeRatesEntity to an external model, used for presentation or other purposes
fun ExchangeRatesEntity.asExternalModel() = ExchangeRates(
    baseCurrency = baseCurrency, // Maps base currency directly
    // Maps exchange rates by extracting only the rate value from CurrencyInfo
    rates = exchangeRates.mapValues { it.value.value },
    lastUpdatedDate = lastUpdatedDate // Maps last updated date directly
)