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

    // Rule for setting up and tearing down the Compose testing environment
    @get: Rule val composeTestRule = createComposeRule()

    // Test to check if the loading indicator is visible in the initial state
    @Test
    fun initialStateAssertLoadingVisible() {
        // Set the content of the Compose UI for testing
        composeTestRule.setContent {
            CurrenzyConverterScreen(
                uiState = CurrenzyConverterUiState(), // Initial empty UI state
                onFromCurrencyChange = {},
                onToCurrencyChange = {},
                onSwap = {}
            )
        }

        // Assert that the loading indicator with tag "loading" exists in the UI
        composeTestRule.onNodeWithTag("loading").assertExists()
    }

    // Test to check if clicking the swap button changes the currencies
    @Test
    fun swapClickAssertChangingCurrencies() {
        // Initial values for from and to currencies
        var fromCurrency = CurrenzyUiModel("USD", "1.0")
        var toCurrency = CurrenzyUiModel("PHP", "56.0")

        // Set the content of the Compose UI for testing
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
                    // Swap currencies on button click
                    val temp = fromCurrency
                    fromCurrency = toCurrency
                    toCurrency = temp
                    uiState = uiState.copy(
                        fromCurrency = fromCurrency,
                        toCurrency = toCurrency
                    )
                }
            )
        }

        // Assert initial state of the currencies
        composeTestRule.onNodeWithText("USD").assertExists()
        composeTestRule.onNodeWithText("PHP").assertExists()

        assert(fromCurrency.code == "USD")
        assert(toCurrency.code == "PHP")

        // Simulate a click on the swap button
        composeTestRule.onNodeWithTag("swap").performClick()

        // Assert that the currencies have been swapped
        assert(fromCurrency.code == "PHP")
        assert(toCurrency.code == "USD")
    }
}