import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import di.initKoin
import presentation.SplashScreen

fun MainViewController() = ComposeUIViewController {
    initKoin()
    Navigator(SplashScreen) { navigator ->
        SlideTransition(navigator)
    }
}
