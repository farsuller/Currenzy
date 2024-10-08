package com.solodev.currenzy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.currenzy.design.theme.CurrenzyTheme
import com.solodev.currenzy.navigation.CurrenzyConverterNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrenzyTheme(
                dynamicColor = false,
                darkTheme = false
            ) {
                CurrenzyConverterNavGraph()
            }
        }
    }
}

