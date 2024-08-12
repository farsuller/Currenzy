package com.solodev.currenzy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.currenzy.currenzyconverter.CurrenzyConverterRoute
import com.currenzy.database.db.CurrencyDatabase
import com.currenzy.database.model.ExchangeRatesEntity
import com.currenzy.design.theme.CurrenzyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var currencyDatabase : CurrencyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            currencyDatabase.dao().upsertExchangeRates(
                ExchangeRatesEntity("","", mapOf())
            )
        }
        setContent {
            CurrenzyTheme {
                CurrenzyConverterRoute()
            }
        }
    }
}

