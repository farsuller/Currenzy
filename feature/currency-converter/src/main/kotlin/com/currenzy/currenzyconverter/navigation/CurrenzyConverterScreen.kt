package com.currenzy.currenzyconverter.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.currenzy.currenzyconverter.CurrenzyConverterRoute

const val CurrenzyConverterRoute = "currenzyConverterRoute"

fun NavGraphBuilder.currenzyConverterScreen() {

    composable(CurrenzyConverterRoute) {
        CurrenzyConverterRoute()
    }
}