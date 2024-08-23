package com.currenzy.currenzyconverter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class CurrenzyConverterScreenTest {

    @get: Rule val composeTestRule = createComposeRule()

    @Test
    fun initialStateAssertLoadingVisible(){
        composeTestRule.setContent {
            CurrenzyConverterScreen(
                uiState = CurrenzyConverterUiState(),
                onFromCurrencyChange = {},
                onToCurrencyChange = {},
                onSwap = {}
            )
        }
        composeTestRule.onNodeWithTag("loading").assertExists()
    }

    @Test
    fun swapClickAssertChangingCurrencies(){

        var fromCurrency = CurrenzyUiModel("USD", " 1.0")
        var toCurrency = CurrenzyUiModel("PHP", "56.0")

        composeTestRule.setContent {

            var uiState by remember {
                mutableStateOf(CurrenzyConverterUiState(
                    fromCurrency = fromCurrency,
                    toCurrency = toCurrency
                ))
            }
            CurrenzyConverterScreen(
                uiState = uiState,
                onFromCurrencyChange = {},
                onToCurrencyChange = {},
                onSwap = {
                    val temp = fromCurrency
                    fromCurrency = toCurrency
                    toCurrency = temp
                    uiState = uiState.copy(
                        fromCurrency = fromCurrency,
                        toCurrency = toCurrency)
                }
            )
        }

        composeTestRule.onNodeWithText("USD").assertExists()
        composeTestRule.onNodeWithText("PHP").assertExists()

        assert(fromCurrency.code == "USD")
        assert(toCurrency.code == "PHP")

        composeTestRule.onNodeWithTag("swap").performClick()

        assert(fromCurrency.code == "PHP")
        assert(toCurrency.code == "USD")
    }

}