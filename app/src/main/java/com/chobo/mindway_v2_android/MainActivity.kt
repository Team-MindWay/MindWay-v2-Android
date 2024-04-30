package com.chobo.mindway_v2_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.chobo.mindway_v2_android.ui.theme.Mindwayv2AndroidTheme
import com.chobo.presentation.view.login.navigation.loginRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            Mindwayv2AndroidTheme {
                MindWayNavHost(startDestination = loginRoute)
            }
        }
    }
}