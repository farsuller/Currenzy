package com.solodev.currenzy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.currenzy.currenzyconverter.CurrenzyConverterScreen
import com.currenzy.design.components.CurrenzyTextMenu
import com.currenzy.design.theme.CurrenzyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrenzyTheme {
                CurrenzyConverterScreen()
            }
        }
    }
}

