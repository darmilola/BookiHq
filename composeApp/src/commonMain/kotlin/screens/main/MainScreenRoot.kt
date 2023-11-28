package screens.main

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

object MainScreenRoot : Screen {
    @Composable
    override fun Content() {
        Navigator(MainScreenLanding)
    }
}