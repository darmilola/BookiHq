
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import di.initKoin
import platform.UIKit.UIViewController
import presentation.SplashScreen


class MainViewController: ProxyNavigator {
    var pageTitle = "SplashScreen"
    var onEventChange: (() -> Unit)? = null

    fun MainViewController(onEventChange:() -> Unit): UIViewController {
        val view = ComposeUIViewController {
            initKoin()

            Navigator(SplashScreen(this)) { navigator ->
                SlideTransition(navigator)
            }

        }
        this.onEventChange = onEventChange
        view.setTitle(pageTitle)
        return view
    }

    override fun openPage(pageTitle: String) {
        this.pageTitle = pageTitle
        onEventChange?.let {
            it()
        }
    }
}