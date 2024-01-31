package presentation.authentication

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginScreenCompose() {
    SignUpLogin(0)

}



object LoginScreen : Screen {
    @Composable
    override fun Content() {
       LoginScreenCompose()
    }
}



