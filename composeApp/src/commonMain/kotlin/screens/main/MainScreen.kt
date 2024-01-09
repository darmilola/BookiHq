package screens.main

import Styles.Colors
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import components.ImageComponent
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import screens.Bookings.BookingScreen
import screens.Bookings.ServiceInformationPage
import screens.Products.CartScreen
import screens.UserProfile.SwitchVendor.ConnectPage
import screens.UserProfile.EditProfile
import screens.UserProfile.SwitchVendor.ConnectPageTab
import screens.UserProfile.UserOrders.UserOrders
import screens.authentication.AuthenticationScreen
import screens.consultation.ConsultationScreen
import screens.consultation.VirtualConsultationRoom

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val mainViewModel = MainViewModel()
        val screenId: State<Int> =  mainViewModel.screenId.observeAsState()

        TabNavigator(showDefaultTab(mainViewModel)) {
            when (screenId.value) {
                0 -> {
                    it.current = MainTab(mainViewModel)
                }
                1 -> {
                    it.current = BookingScreen(mainViewModel)
                }
                2 -> {
                    it.current = ConsultationScreen(mainViewModel)
                }
                3 -> {
                    it.current = CartScreen(mainViewModel)
                }
                5 -> {
                    it.current = UserOrders(mainViewModel)
                }
                6 -> {
                    it.current = ConnectPageTab(mainViewModel)
                }
                8 -> {
                    it.current = VirtualConsultationRoom(mainViewModel)
                }
                9 -> {
                    it.current = EditProfile(mainViewModel)
                }
            }

            Scaffold(
                topBar = {

                },
                content = {
                    mainViewModel.setId(-1)
                    CurrentTab()
                })

        }

    }

    private fun showDefaultTab(mainViewModel: MainViewModel): MainTab {

        return  MainTab(mainViewModel)
    }

}
