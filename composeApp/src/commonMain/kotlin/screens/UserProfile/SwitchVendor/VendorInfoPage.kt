package screens.UserProfile.SwitchVendor

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import screens.main.MainScreen

object VendorInfoPage : Screen {
    @Composable
    override fun Content() {
        Scaffold(
            topBar = {
                BusinessInfoTitle()
            },
            content = {
                val navigator = LocalNavigator.currentOrThrow
                    BusinessInfoContent {
                        navigator.replaceAll(MainScreen)
                    }

            },
            backgroundColor = Color.Transparent,
        )
    }
}
