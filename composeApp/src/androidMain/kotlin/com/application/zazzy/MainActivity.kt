package com.application.zazzy

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import di.initKoin
import presentation.SplashScreen
import presentation.SplashScreenCompose

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKoin()
        enableEdgeToEdge()
        setContent {
            Navigator(SplashScreen)
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          //  window.statusBarColor = ContextCompat.getColor(this, R.color.light_background_color)
           // window.navigationBarColor = ContextCompat.getColor(this, R.color.light_background_color)
        }
    }

}

@Preview
@Composable
fun AppAndroidPreview() {
    SplashScreenCompose()
}