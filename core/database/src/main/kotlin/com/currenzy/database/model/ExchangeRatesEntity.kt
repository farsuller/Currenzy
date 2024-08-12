package com.currenzy.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currenzy.model.currency_converter.CurrencyInfo

@Entity
data class ExchangeRatesEntity(
    @PrimaryKey
    val lastUpdateDate : String,
    val baseCurrency : String,
    val exchangeRates : Map<String, CurrencyInfo>
)