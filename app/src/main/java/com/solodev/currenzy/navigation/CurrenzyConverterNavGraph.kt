package com.solodev.currenzy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.currenzy.currenzyconverter.navigation.CurrenzyConverterRoute
import com.currenzy.currenzyconverter.navigation.currenzyConverterScreen

@Composable
fun CurrenzyConverterNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = CurrenzyConverterRoute) {
        currenzyConverterScreen()
    }
}