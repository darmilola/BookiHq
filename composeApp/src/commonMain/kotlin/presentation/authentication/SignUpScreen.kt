package presentation.authentication

import ProxyNavigator
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignUpScreenCompose(proxyNavigator: ProxyNavigator) {



   SignUpLogin(1, proxyNavigator)

}


class SignUpScreen(val proxyNavigator: ProxyNavigator) : Screen {
    @Composable
    override fun Content() {
        SignUpScreenCompose(proxyNavigator)
    }
}

