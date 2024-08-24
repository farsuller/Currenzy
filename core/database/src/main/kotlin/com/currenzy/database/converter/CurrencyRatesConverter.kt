package com.currenzy.database.converter

import androidx.room.TypeConverter
import com.currenzy.model.currency_converter.CurrencyInfo

// Converter class to handle the conversion between Map<String, CurrencyInfo> and String for Room database
class CurrencyRatesConverter {

    // Converts a Map<String, CurrencyInfo> to a String representation
    @TypeConverter
    fun fromCurrenciesMapToString(currencies: Map<String, CurrencyInfo>): String {
        var result = ""
        // Iterate over the map entries to build a string representation
        currencies.forEach {
            // Format each entry as "key:valueCode,valueValue/"
            result += "${it.key}:${it.value.code},${it.value.value}/"
        }
        // Return the resulting string
        return result
    }

    // Converts a String representation back to a Map<String, CurrencyInfo>
    @TypeConverter
    fun fromCurrenciesStringToMap(string: String): Map<String, CurrencyInfo> {
        val result = mutableMapOf<String, CurrencyInfo>()
        // Split the string into map entries using "/" as the delimiter
        val mapEntries = string.split("/")
        for (mapEntry in mapEntries) {
            // Split each entry into key and value parts using ":" as the delimiter
            val keyValue = mapEntry.split(":")
            val key = keyValue.firstOrNull() ?: break
            val value = keyValue.getOrNull(1)?.split(",") ?: break

            // Create a CurrencyInfo object from the value part
            val currencyInfo = CurrencyInfo(value.first(), value.last().toDouble())

            // Add the key-value pair to the result map
            result[key] = currencyInfo
        }

        // Return the resulting map
        return result
    }
}