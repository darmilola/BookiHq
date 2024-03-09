package presentation.UserProfile.SwitchVendor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import domain.Models.Auth0ConnectionResponse
import domain.Models.PlatformNavigator


open class ConnectPage() : Screen {

    private val preferenceSettings: Settings = Settings()
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                SwitchVendorHeader()
            },
            content = {
                LazyColumn(
                    modifier = Modifier.padding(top = 10.dp, bottom = 40.dp).fillMaxWidth().fillMaxHeight(),
                    contentPadding = PaddingValues(6.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(10) {
                        ConnectBusinessItemComponent {
                            navigator.push(VendorInfoPage)
                        }
                    }
                }
            },
            backgroundColor = Color.White,
        )
    }


}