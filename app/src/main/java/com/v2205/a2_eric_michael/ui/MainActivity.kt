package com.v2205.a2_eric_michael.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.v2205.a2_eric_michael.ui.theme.A2_Eric_MichaelTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.v2205.a2_eric_michael.navigation.NavigationApp

class MainActivity : ComponentActivity() {
    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            A2_Eric_MichaelTheme {
                NavigationApp()
            }
        }
    }
}
