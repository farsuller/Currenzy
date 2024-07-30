package com.currenzy.model.currency_converter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(

    @SerialName("last_updated_at")
    val lastUpdatedAt: String
)
