package screens.UserProfile.SwitchVendor

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen

object VendorInfoPage : Screen {
    @Composable
    override fun Content() {
        Scaffold(
            topBar = {
                BusinessInfoTitle()
            },
            content = {

                    BusinessInfoContent {}

            },
            backgroundColor = Color.Transparent,
        )
    }
}
