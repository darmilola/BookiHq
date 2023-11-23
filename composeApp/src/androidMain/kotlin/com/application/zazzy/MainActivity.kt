package com.application.zazzy

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.navigator.Navigator
import screens.SplashScreen
import screens.SplashScreenCompose

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigator(SplashScreen)
        }
    }

    override fun onResume() {
        super.onResume()

    }

}

@Preview
@Composable
fun AppAndroidPreview() {
    SplashScreenCompose()
}