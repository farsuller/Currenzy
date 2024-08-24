package com.currenzy.currenzyconverter

/**
 * A utility object for converting amounts between different currencies.
 *
 * This object provides a method to convert an amount from one currency to another based on their exchange rates relative to a base currency.
 */
object CurrenzyConverter {

    /**
     * Converts an amount from one currency to another using their exchange rates.
     *
     * This method calculates the equivalent amount in the target currency by taking into account the exchange rates
     * of both the source and target currencies relative to a common base currency.
     *
     * @param fromCurrencyRateVsBaseCurrencyRate The exchange rate of the source currency relative to the base currency.
     * @param toCurrencyRateVsBaseCurrencyRate The exchange rate of the target currency relative to the base currency.
     * @param amount The amount in the source currency that needs to be converted.
     * @return The equivalent amount in the target currency.
     *
     * The conversion formula used is:
     * (toCurrencyRateVsBaseCurrencyRate / fromCurrencyRateVsBaseCurrencyRate) * amount
     */
    fun convert(
        fromCurrencyRateVsBaseCurrencyRate: Double,
        toCurrencyRateVsBaseCurrencyRate: Double,
        amount: Double
    ): Double {
        return (toCurrencyRateVsBaseCurrencyRate / fromCurrencyRateVsBaseCurrencyRate) * amount
    }
}