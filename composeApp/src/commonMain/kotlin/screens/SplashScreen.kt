package screens

import AppTheme.AppColors
import AppTheme.AppBoldTypography
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.PinkGradientBackground
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screens.authentication.WelcomeScreen
import widgets.SplashScreenWidget

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreenCompose() {
   val  modifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(color = 0xFFF43569))
    val navigator = LocalNavigator.currentOrThrow
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        // AnimationEffect
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            PinkGradientBackground()
            SplashScreenWidget(textStyle = MaterialTheme.typography.h6)
        }
        LaunchedEffect(key1 = true) {
            delay(3000L)
            navigator.push(WelcomeScreen)
        }
    }

 }
object SplashScreen : Screen {
    @Composable
    override fun Content() {
        SplashScreenCompose()
    }
}

