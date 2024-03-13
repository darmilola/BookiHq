package presentation.UserProfile.ConnectVendor

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import presentation.viewmodels.MainViewModel

class ConnectedVendorDetailsPage(private val mainViewModel: MainViewModel) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "ConnectVendor Info"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }

    @Composable
    override fun Content() {
        val connectedVendor = mainViewModel.connectedVendor.value
        Scaffold(
            topBar = {
                BusinessInfoTitle(mainViewModel = mainViewModel)
            },
            content = {
                val navigator = LocalTabNavigator.current
                BusinessInfoContent(connectedVendor, isUserAuthenticated = true){}
            },
            backgroundColor = Color.Transparent,
        )
    }
}
