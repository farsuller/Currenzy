package com.currenzy.currenzyconverter

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.currenzy.design.R
import com.currenzy.design.components.CurrenzyBackgroundScreen
import com.currenzy.design.components.CurrenzyCard
import com.currenzy.design.components.CurrenzyTextField
import com.currenzy.design.components.CurrenzyTextMenu
import com.currenzy.design.theme.CurrenzyTheme
import kotlinx.coroutines.launch

@Composable
internal fun CurrenzyConverterRoute(
    viewModel: CurrenzyConverterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CurrenzyConverterScreen(
        uiState = uiState,
        onFromCurrencyChange = viewModel::onFromCurrencyChange,
        onToCurrencyChange = viewModel::onToCurrencyChange,
        onSwap = viewModel::swapCurrencies
    )
}

@Composable
internal fun CurrenzyConverterScreen(
    uiState: CurrenzyConverterUiState,
    onFromCurrencyChange: (CurrenzyUiModel) -> Unit,
    onToCurrencyChange: (CurrenzyUiModel) -> Unit,
    onSwap: () -> Unit
) {
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
                allCurrencies = uiState.allCurrencies,
                fromCurrency = uiState.fromCurrency,
                toCurrency = uiState.toCurrency,
                onFromCurrencyChanged = onFromCurrencyChange,
                onToCurrencyChanged = onToCurrencyChange,
                onSwap = onSwap
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "${stringResource(id = R.string.indicative_exchange_rate)} ${uiState.lastUpdated}",
                modifier = Modifier.padding(horizontal = 22.dp),
                style = MaterialTheme.typography.labelSmall
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = uiState.indicativeExchangeRate,
                modifier = Modifier.padding(horizontal = 22.dp),
                style = MaterialTheme.typography.titleMedium.copy(color = Color.Black)
            )


            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                )
                {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(30.dp)
                            .testTag("loading")
                    )
                }
            }
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
    onSwap: () -> Unit,
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
        Spacer(modifier = Modifier.height(20.dp))

        CurrenzySwapper(onSwap = onSwap)
        Spacer(modifier = Modifier.height(10.dp))

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

@Composable
fun CurrenzySwapper(
    modifier: Modifier = Modifier,
    onSwap: () -> Unit
) {
    val animatable = remember {
        Animatable(0F)
    }

    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalDivider()
        Box(
            modifier = modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    if (animatable.isRunning)
                        return@clickable

                    scope.launch {
                        onSwap()
                        animatable.animateTo(
                            animatable.value + 180F,
                            tween(300)
                        )
                    }
                }
                .testTag("swap"),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_trade
                ),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(10.dp)
                    .rotate(animatable.value)
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
            onSwap = {}
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
        CurrenzyConverterScreen(
            uiState = CurrenzyConverterUiState.PreviewData,
            onFromCurrencyChange = {},
            onToCurrencyChange = {},
            onSwap = {}
        )
    }
}