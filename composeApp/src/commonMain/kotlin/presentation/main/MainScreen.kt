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
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
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
import presentation.authentication.AuthenticationPresenter
import presentation.connectVendor.ConnectVendorDetailsScreen
import presentation.home.ViewConnectedVendorDetailsTab
import presentation.profile.JoinDetailsTab
import presentation.profile.TalkWithATherapist
import presentation.therapist.TherapistDashboardTab
import presentation.viewmodels.MainViewModel
import utils.ParcelableScreen

@Parcelize
class MainScreen(val platformNavigator: PlatformNavigator? = null) : ParcelableScreen, KoinComponent {

     @Transient private var mainViewModel: MainViewModel? = null
     @Transient private val preferenceSettings: Settings = Settings()
     private var userId: Long = 0L
     private var mainTab: MainTab? = null
     private var joinASpa: JoinASpa? = null
     @Transient
     private val  authenticationPresenter: AuthenticationPresenter by inject()

    @Composable
    override fun Content() {

        val imageUploading = remember { mutableStateOf(false) }
        val locationRequestAllowed = remember { mutableStateOf(false) }
        userId = preferenceSettings.getLong("profileId",0L)


    if (mainViewModel == null) {
        mainViewModel = kmpViewModel(
            factory = viewModelFactory {
                MainViewModel(savedStateHandle = createSavedStateHandle())
            },
        )
        platformNavigator!!.startNotificationService {
            authenticationPresenter.updateFcmToken(userId = userId, fcmToken = it)
        }
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
                    val consultTab = ConsultationScreen()
                    consultTab.setMainViewModel(mainViewModel!!)
                    it.current = consultTab
                }
                Screens.CART.toPath() -> {
                    val cart = CartTab()
                    cart.setMainViewModel(mainViewModel!!)
                    it.current = cart
                }
                Screens.ORDERS.toPath() -> {
                    val orders = Orders()
                    orders.setMainViewModel(mainViewModel!!)
                    it.current = orders
                }
                Screens.CONNECT_VENDOR_TAB.toPath() -> {
                    val connectVendorTab = ConnectVendorTab(platformNavigator)
                    connectVendorTab.setMainViewModel(mainViewModel!!)
                    it.current = connectVendorTab
                }
                Screens.CONSULTATION_ROOM.toPath() -> {
                    val consultation = VirtualConsultationRoom()
                    consultation.setMainViewModel(mainViewModel!!)
                    it.current = consultation
                }
                Screens.EDIT_PROFILE.toPath() -> {
                    val editProfileTab = EditProfileTab(platformNavigator)
                    editProfileTab.setMainViewModel(mainViewModel!!)
                    it.current = editProfileTab
                }
                Screens.VENDOR_INFO.toPath() -> {
                    val switchVendor = SwitchVendorDetailsTab()
                    switchVendor.setMainViewModel(mainViewModel!!)
                    it.current = switchVendor
                }
                Screens.PENDING_APPOINTMENT.toPath() -> {
                    val pendingAppointmentsTab = PendingAppointmentsTab()
                    pendingAppointmentsTab.setMainViewModel(mainViewModel!!)
                    it.current = pendingAppointmentsTab
                }
                Screens.THERAPIST_DASHBOARD.toPath() -> {
                    val dashboard = TherapistDashboardTab()
                    dashboard.setMainViewModel(mainViewModel!!)
                    it.current = dashboard
                }
                Screens.JOIN_SPA.toPath() -> {
                    joinASpa = JoinASpa(platformNavigator!!)
                    joinASpa!!.setMainViewModel(mainViewModel!!)
                    it.current = joinASpa!!
                }
                Screens.TALK_WITH_A_THERAPIST.toPath() -> {
                    val talkWithTherapist = TalkWithATherapist()
                    talkWithTherapist.setMainViewModel(mainViewModel!!)
                    it.current = talkWithTherapist
                }
                Screens.CONNECTED_VENDOR_DETAILS.toPath() -> {
                    val details = ViewConnectedVendorDetailsTab()
                    details.setMainViewModel(mainViewModel!!)
                    it.current = details
                }
                Screens.JOIN_SPA_INFO.toPath() -> {
                    val details = JoinDetailsTab()
                    details.setMainViewModel(mainViewModel!!)
                    it.current = details
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
