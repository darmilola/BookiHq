package presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import dev.icerock.moko.parcelize.Parcelize
import domain.Models.PlatformNavigator
import domain.Enums.Screens
import org.koin.core.component.KoinComponent
import presentation.Orders.Orders
import presentation.bookings.BookingScreen
import presentation.bookings.PendingAppointmentsTab
import presentation.Products.CartTab
import presentation.Splashscreen.SplashScreen
import presentation.profile.EditProfileTab
import presentation.connectVendor.ConnectVendorTab
import presentation.connectVendor.SwitchVendorDetailsTab
import presentation.consultation.ConsultationScreen
import presentation.consultation.VirtualConsultationRoom
import presentation.dialogs.LoadingDialog
import presentation.account.JoinASpa
import presentation.profile.TalkWithATherapist
import presentation.therapist.TherapistDashboardTab
import presentation.viewmodels.MainViewModel
import utils.ParcelableScreen

@Parcelize
class MainScreen(val platformNavigator: PlatformNavigator? = null) : ParcelableScreen, KoinComponent {

     private var mainViewModel: MainViewModel? = null
     private val preferenceSettings: Settings = Settings()
     private var mainTab: MainTab? = null

    @Composable
    override fun Content() {



   val imageUploading = remember { mutableStateOf(false) }
   val locationRequestAllowed = remember { mutableStateOf(false) }

    if (mainViewModel == null) {
        mainViewModel = kmpViewModel(
            factory = viewModelFactory {
                MainViewModel(savedStateHandle = createSavedStateHandle())
            },
        )
    }

        preferenceSettings as ObservableSettings

        preferenceSettings.addBooleanListener("imageUploadProcessing",false) {
                value: Boolean -> imageUploading.value = value
        }

        preferenceSettings.addBooleanListener("locationRequestAllowed",false) {
                value: Boolean -> locationRequestAllowed.value = value
        }

         if(imageUploading.value) {
            Box(modifier = Modifier.fillMaxWidth(0.80f)) {
                LoadingDialog("Uploading...")
            }
        }

        if(locationRequestAllowed.value) {
            Box(modifier = Modifier.fillMaxWidth(0.80f)) {
                LoadingDialog("Fetching Location...")
            }
        }


        val screenNav: State<Pair<Int, Int>>? = mainViewModel?.screenNav?.collectAsState()
        val restartApp = mainViewModel!!.restartApp.collectAsState()

        if (restartApp.value){
            val navigator = LocalNavigator.currentOrThrow
            navigator.replaceAll(SplashScreen(platformNavigator!!))
        }


        TabNavigator(showDefaultTab(mainViewModel!!)) {
            when (screenNav?.value?.second) {
                Screens.MAIN_TAB.toPath() -> {
                    mainTab =  MainTab(platformNavigator!!)
                    mainTab!!.setMainViewModel(mainViewModel!!)
                    it.current = mainTab!!
                }
                Screens.BOOKING.toPath() -> {
                    val bookingTab = BookingScreen()
                    bookingTab.setMainViewModel(mainViewModel!!)
                    it.current = bookingTab
                }
                Screens.CONSULTATION.toPath() -> {
                    it.current = ConsultationScreen(mainViewModel!!)
                }
                Screens.CART.toPath() -> {
                    it.current = CartTab(mainViewModel!!)
                }
                Screens.ORDERS.toPath() -> {
                    it.current = Orders(mainViewModel!!)
                }
                Screens.CONNECT_VENDOR_TAB.toPath() -> {
                    it.current = ConnectVendorTab(mainViewModel!!, platformNavigator)
                }
                Screens.CONSULTATION_ROOM.toPath() -> {
                    it.current = VirtualConsultationRoom(mainViewModel!!)
                }
                Screens.EDIT_PROFILE.toPath() -> {
                    it.current = EditProfileTab(mainViewModel!!, platformNavigator)
                }
                Screens.VENDOR_INFO.toPath() -> {
                    it.current = SwitchVendorDetailsTab(mainViewModel!!,platformNavigator!!)
                }
                Screens.PENDING_APPOINTMENT.toPath() -> {
                    it.current = PendingAppointmentsTab(mainViewModel!!)
                }
                Screens.THERAPIST_DASHBOARD.toPath() -> {
                    it.current = TherapistDashboardTab(mainViewModel!!)
                }
                Screens.JOIN_SPA.toPath() -> {
                    it.current = JoinASpa(mainViewModel!!)
                }
                Screens.TALK_WITH_A_THERAPIST.toPath() -> {
                    it.current = TalkWithATherapist(mainViewModel!!)
                }
            }

            Scaffold(
                content = { CurrentTab() })
        }
    }

    private fun showDefaultTab(mainViewModel: MainViewModel): MainTab {
        mainTab =  MainTab(platformNavigator!!)
        mainTab!!.setMainViewModel(mainViewModel)
        return mainTab!!
    }

}
