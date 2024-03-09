package presentation

import domain.Models.PlatformNavigator
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import di.initKoin
import presentation.components.SplashScreenBackground
import kotlinx.coroutines.delay
import presentation.authentication.WelcomeScreen
import presentation.widgets.SplashScreenWidget

@Composable
fun SplashScreenCompose(platformNavigator: PlatformNavigator) {
   val  modifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(color = 0xFFF43569))
    val navigator = LocalNavigator.currentOrThrow
        // AnimationEffect
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            SplashScreenBackground()
            SplashScreenWidget(textStyle = TextStyle())
            Box(modifier = Modifier
                .padding(bottom = 70.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter) {
            }
        }
        LaunchedEffect(key1 = true) {
            delay(3000L)
            navigator.replaceAll(WelcomeScreen(platformNavigator))
        }
    }

class SplashScreen(val platformNavigator: PlatformNavigator) : Screen {
    @Composable
    override fun Content() {
        initKoin()
        SplashScreenCompose(platformNavigator = platformNavigator)
    }
}

