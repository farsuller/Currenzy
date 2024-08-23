import com.currenzy.currenzyconverter.CurrenzyConverter
import org.junit.Test

class CurrenzyConverterTest {

    @Test
    fun `convert value in USD to PHP`(){
        val usdVsUsdPrice = 1.0
        val usdVsPhpPrice = 56.0
        val result = CurrenzyConverter.convert(
            fromCurrencyRateVsBaseCurrencyRate = usdVsUsdPrice,
            toCurrencyRateVsBaseCurrencyRate = usdVsPhpPrice,
            amount = 1.0,
            )

        assert(result == 56.0)
    }

    @Test
    fun `convert value USD to USD`(){
        val usdVsUsdPrice = 1.0
        val result = CurrenzyConverter.convert(
            fromCurrencyRateVsBaseCurrencyRate = usdVsUsdPrice,
            toCurrencyRateVsBaseCurrencyRate = usdVsUsdPrice,
            amount = 20.5,
        )
        assert(result == 20.5)
    }

    @Test
    fun `convert zero amount`(){
        val result = CurrenzyConverter.convert(
            fromCurrencyRateVsBaseCurrencyRate = 1.0,
            toCurrencyRateVsBaseCurrencyRate = 1.0,
            amount = 0.0,
        )
        assert(result == 0.0)
    }
}