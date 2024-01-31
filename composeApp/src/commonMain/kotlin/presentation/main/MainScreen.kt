package presentation.main

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import presentation.Bookings.BookingScreen
import presentation.Bookings.PendingAppointmentsTab
import presentation.Products.CartScreen
import presentation.UserProfile.EditProfile
import presentation.UserProfile.SwitchVendor.ConnectPageTab
import presentation.UserProfile.SwitchVendor.SwitchVendorInfoPage
import presentation.UserProfile.UserOrders.UserOrders
import presentation.consultation.ConsultationScreen
import presentation.consultation.VirtualConsultationRoom
import presentation.viewmodels.MainViewModel

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
                10 -> {
                    it.current = SwitchVendorInfoPage(mainViewModel)
                }
                11 -> {
                    it.current = PendingAppointmentsTab(mainViewModel)
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
