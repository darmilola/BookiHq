package presentation.UserProfile.SwitchVendor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import presentation.main.MainViewModel

class ConnectPageTab(private val mainViewModel: MainViewModel) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "ConnectPage"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }

    @Composable
    override fun Content() {
        val navigator = LocalTabNavigator.current
        Scaffold(
            topBar = {
                SwitchVendorHeader(mainViewModel)
            },
            content = {
                LazyColumn(
                    modifier = Modifier.padding(top = 10.dp, bottom = 40.dp).fillMaxWidth().fillMaxHeight(),
                    contentPadding = PaddingValues(6.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(10) {
                        ConnectBusinessItemComponent {
                            mainViewModel.setFromId(6)
                            mainViewModel.setId(10)
                        }
                    }
                }
            },
            backgroundColor = Color.White,
        )
    }
}