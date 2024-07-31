package com.currenzy.currenzyconverter

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.currenzy.design.R
import com.currenzy.design.components.CurrenzyBackgroundScreen
import com.currenzy.design.components.CurrenzyCard
import com.currenzy.design.components.CurrenzyTextField
import com.currenzy.design.components.CurrenzyTextMenu
import com.currenzy.design.theme.CurrenzyTheme

@Composable
internal fun CurrenzyConverterRoute() {
}

@Composable
internal fun CurrenzyConverterScreen() {
    CurrenzyBackgroundScreen {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(id = R.string.currenzy_converter),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.currenzy_converter_description),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(50.dp))


            CurrenzyConverterCard(
                allCurrencies = listOf(),
                fromCurrency = CurrenzyUiModel("USD", ""),
                toCurrency = CurrenzyUiModel("PHP", ""),
                onFromCurrencyChanged = {},
                onToCurrencyChanged = {},
                swapCurrency = {}
            )

        }
    }
}

@Composable
private fun CurrenzyConverterCard(
    modifier: Modifier = Modifier,
    allCurrencies: List<CurrenzyUiModel>,
    fromCurrency: CurrenzyUiModel,
    toCurrency: CurrenzyUiModel,
    onFromCurrencyChanged: (CurrenzyUiModel) -> Unit,
    onToCurrencyChanged: (CurrenzyUiModel) -> Unit,
    swapCurrency: () -> Unit,
) {
    CurrenzyCard(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        CurrenzyInfoRow(
            label = stringResource(id = R.string.amount),
            selectedCurrency = fromCurrency,
            currencies = allCurrencies,
            onCurrencyChange = onFromCurrencyChanged
        )
        Spacer(modifier = Modifier.height(30.dp))

        CurrenzyInfoRow(
            label = stringResource(id = R.string.converted_amount),
            selectedCurrency = toCurrency,
            currencies = allCurrencies,
            onCurrencyChange = onToCurrencyChanged
        )


    }
}

@Composable
private fun CurrenzyInfoRow(
    modifier: Modifier = Modifier,
    label: String,
    selectedCurrency: CurrenzyUiModel,
    currencies: List<CurrenzyUiModel>,
    onCurrencyChange: (CurrenzyUiModel) -> Unit
) {
    val currencyCodes = remember(currencies) { currencies.map { it.code } }
    Column(
        modifier = modifier
    ) {
        Text(text = label, style = MaterialTheme.typography.labelSmall)

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                modifier = Modifier.weight(1f),
                targetState = selectedCurrency.code, label = "CurrenzyInfoRow"
            )
            {
                CurrenzyTextMenu(
                    selectedOption = it,
                    options = currencyCodes,
                    onOptionSelected = { index ->
                        onCurrencyChange(currencies[index].copy(value = selectedCurrency.value))
                    })

            }

            Spacer(modifier = Modifier.height(30.dp))

            CurrenzyTextField(
                modifier = Modifier.weight(1f),
                value = selectedCurrency.value,
                onValueChange = { onCurrencyChange(selectedCurrency.copy(value = it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

        }
    }

}

@Preview
@Composable
internal fun CurrenzyConverterCardPreview() {
    CurrenzyTheme {
        CurrenzyConverterCard(
            allCurrencies = listOf(),
            fromCurrency = CurrenzyUiModel("USD", "1500"),
            toCurrency = CurrenzyUiModel("USD", "1500"),
            onFromCurrencyChanged = {},
            onToCurrencyChanged = {},
            swapCurrency = {}
        )
    }
}

@Preview
@Composable
internal fun CurrenzyInfoRowPreview() {
    CurrenzyTheme {
        CurrenzyInfoRow(
            label = "Amount",
            selectedCurrency = CurrenzyUiModel("USD", "1500"),
            currencies = listOf(),
            onCurrencyChange = {}
        )
    }
}


@Preview
@Composable
internal fun CurrenzyConverterScreenPreview() {
    CurrenzyTheme {
        CurrenzyConverterScreen()
    }
}