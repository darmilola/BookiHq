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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import domain.Models.PlatformNavigator
import domain.Models.Screens
import org.koin.core.component.KoinComponent
import presentation.Orders.Orders
import presentation.bookings.BookingScreen
import presentation.bookings.PendingAppointmentsTab
import presentation.Products.CartScreen
import presentation.profile.EditProfile
import presentation.profile.connect_vendor.ConnectPageTab
import presentation.profile.connect_vendor.ConnectedVendorDetailsPage
import presentation.consultation.ConsultationScreen
import presentation.consultation.VirtualConsultationRoom
import presentation.dialogs.LoadingDialog
import presentation.main.account.JoinASpa
import presentation.profile.TalkWithATherapist
import presentation.therapist.TherapistDashboardTab
import presentation.viewmodels.MainViewModel

class MainScreen(val platformNavigator: PlatformNavigator? = null) : Screen, KoinComponent {

    private var mainViewModel: MainViewModel? = null
    private val preferenceSettings: Settings = Settings()

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
        saveAccountInfoToViewModel(mainViewModel!!)
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

        TabNavigator(showDefaultTab(mainViewModel!!)) {
            when (screenNav?.value?.second) {
                Screens.MAIN_TAB.toPath() -> {
                    it.current = MainTab(mainViewModel!!, platformNavigator!!)
                }
                Screens.BOOKING.toPath() -> {
                    it.current = BookingScreen(mainViewModel!!)
                }
                Screens.CONSULTATION.toPath() -> {
                    it.current = ConsultationScreen(mainViewModel!!)
                }
                Screens.CART.toPath() -> {
                    it.current = CartScreen(mainViewModel!!)
                }
                Screens.ORDERS.toPath() -> {
                    it.current = Orders(mainViewModel!!)
                }
                Screens.CONNECT_VENDOR_PAGE.toPath() -> {
                    it.current = ConnectPageTab(mainViewModel!!, platformNavigator)
                }
                Screens.CONSULTATION_ROOM.toPath() -> {
                    it.current = VirtualConsultationRoom(mainViewModel!!)
                }
                Screens.EDIT_PROFILE.toPath() -> {
                    it.current = EditProfile(mainViewModel!!, platformNavigator, preferenceSettings)
                }
                Screens.VENDOR_INFO.toPath() -> {
                    it.current = ConnectedVendorDetailsPage(mainViewModel!!)
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
    private fun saveAccountInfoToViewModel(mainViewModel: MainViewModel){
        mainViewModel.setUserEmail(preferenceSettings["userEmail",""])
        mainViewModel.setUserFirstname(preferenceSettings["userFirstname",""])
        mainViewModel.setUserId(preferenceSettings["userId",-1])
        mainViewModel.setVendorEmail(preferenceSettings["vendorEmail",""])
        mainViewModel.setVendorId(preferenceSettings["vendorId",-1])
        mainViewModel.setTherapistId(preferenceSettings["therapistId",-1])
        mainViewModel.setVendorBusinessLogoUrl(preferenceSettings["vendorBusinessLogoUrl",""])
    }

    fun setImageUploadResponse(imageUrl: String) {
        preferenceSettings["imageUrl"] = imageUrl
    }
   fun setImageUploadProcessing(isDone: Boolean = false) {
        preferenceSettings["imageUploadProcessing"] = isDone
    }

    fun setLocationRequestAllowed(isAllowed: Boolean) {
        preferenceSettings["locationRequestAllowed"] = isAllowed
    }

    fun setLocationResponse(latitude: Double, longitude: Double) {
        preferenceSettings["latitude"] = latitude
        preferenceSettings["longitude"] = longitude
    }

    private fun showDefaultTab(mainViewModel: MainViewModel): MainTab {
        return  MainTab(mainViewModel, platformNavigator!!)
    }

}
