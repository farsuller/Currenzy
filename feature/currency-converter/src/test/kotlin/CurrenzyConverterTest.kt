import com.currenzy.currenzyconverter.CurrenzyConverter
import org.junit.Test

class CurrenzyConverterTest {

    // Test to convert 1 USD to PHP based on given conversion rates
    @Test
    fun `convert value in USD to PHP`() {
        // Exchange rates: 1 USD = 1 USD and 1 USD = 56 PHP
        val usdVsUsdPrice = 1.0
        val usdVsPhpPrice = 56.0

        // Convert 1 USD to PHP
        val result = CurrenzyConverter.convert(
            fromCurrencyRateVsBaseCurrencyRate = usdVsUsdPrice, // USD to base (USD) rate
            toCurrencyRateVsBaseCurrencyRate = usdVsPhpPrice, // Base (USD) to PHP rate
            amount = 1.0 // Amount to convert
        )

        // Assert that the result is 56 PHP
        assert(result == 56.0)
    }

    // Test to convert 20.5 USD to USD, which should remain the same
    @Test
    fun `convert value USD to USD`() {
        // Exchange rate: 1 USD = 1 USD
        val usdVsUsdPrice = 1.0

        // Convert 20.5 USD to USD
        val result = CurrenzyConverter.convert(
            fromCurrencyRateVsBaseCurrencyRate = usdVsUsdPrice, // USD to base (USD) rate
            toCurrencyRateVsBaseCurrencyRate = usdVsUsdPrice, // Base (USD) to USD rate
            amount = 20.5 // Amount to convert
        )

        // Assert that the result is 20.5 USD
        assert(result == 20.5)
    }

    // Test to convert 0 amount, which should also result in 0
    @Test
    fun `convert zero amount`() {
        // Exchange rate: 1 USD = 1 USD and 1 USD = 1 (base) unit
        val result = CurrenzyConverter.convert(
            fromCurrencyRateVsBaseCurrencyRate = 1.0, // USD to base rate
            toCurrencyRateVsBaseCurrencyRate = 1.0, // Base to USD rate
            amount = 0.0 // Amount to convert
        )

        // Assert that the result is 0.0
        assert(result == 0.0)
    }
}