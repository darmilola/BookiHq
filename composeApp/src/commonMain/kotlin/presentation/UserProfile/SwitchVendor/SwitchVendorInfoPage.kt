package presentation.UserProfile.SwitchVendor

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import presentation.viewmodels.MainViewModel

class SwitchVendorInfoPage(private val mainViewModel: MainViewModel) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Vendor Info"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }

    @Composable
    override fun Content() {
        Scaffold(
            topBar = {
                SwitchBusinessInfoTitle(mainViewModel = mainViewModel)
            },
            content = {
                val navigator = LocalTabNavigator.current
                BusinessInfoContent {
                    navigator.current = ConnectPageTab(mainViewModel = mainViewModel)
                }

            },
            backgroundColor = Color.Transparent,
        )
    }
}
