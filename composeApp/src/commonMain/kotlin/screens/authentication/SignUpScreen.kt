package screens.authentication

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignUpScreenCompose() {



   SignUpLogin(1)

}


object SignUpScreen : Screen {
    @Composable
    override fun Content() {
        SignUpScreenCompose()
    }
}

