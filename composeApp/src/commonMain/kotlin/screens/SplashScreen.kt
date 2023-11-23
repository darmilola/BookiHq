package screens

import AppTheme.AppColors
import AppTheme.AppTypography
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.PinkGradientBackground
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import screens.authentication.WelcomeScreen
import widgets.SplashScreenWidget

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreenCompose() {
   val  modifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight()
    val navigator = LocalNavigator.currentOrThrow
    MaterialTheme(colors = AppColors(), typography = AppTypography()) {
        // AnimationEffect
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            PinkGradientBackground()
            SplashScreenWidget(textStyle = MaterialTheme.typography.h1)
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

