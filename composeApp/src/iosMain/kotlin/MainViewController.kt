import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import screens.SplashScreen

fun MainViewController() = ComposeUIViewController {
    Navigator(SplashScreen) { navigator ->
        SlideTransition(navigator)
    }
}
