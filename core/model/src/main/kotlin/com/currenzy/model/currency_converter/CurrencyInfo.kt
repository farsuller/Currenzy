package com.currenzy.model.currency_converter

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyInfo(
    val code: String,
    val value : Double
)
