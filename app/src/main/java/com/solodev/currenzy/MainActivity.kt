package com.solodev.currenzy

import TestCompose
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.currenzy.model.currency_converter.CurrencyInfo
import com.currenzy.network.service.CurrencyService
import com.solodev.currenzy.ui.theme.CurrenzyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var service : CurrencyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CurrenzyTheme {
                val currencyMapState = remember { mutableStateOf<Map<String, CurrencyInfo>?>(null) }
                val coroutineScope = rememberCoroutineScope()
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        currencyMapState.value = service.getExchangeRates().data
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val currencyList = currencyMapState.value?.values?.toList()

                    if(currencyList != null){
                        LazyColumn(modifier = Modifier.padding(
                            top = innerPadding.calculateTopPadding(),
                            bottom = innerPadding.calculateBottomPadding())) {
                            items(currencyList) { currencyInfo ->
                                Row {
                                    Text(text = currencyInfo.code)
                                    Text(text = currencyInfo.value.toString())
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

