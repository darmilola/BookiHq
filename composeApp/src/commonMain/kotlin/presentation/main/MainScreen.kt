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
import org.koin.core.component.inject
import presentation.bookings.BookingScreen
import presentation.bookings.PendingAppointmentsTab
import presentation.Products.CartScreen
import presentation.profile.EditProfile
import presentation.profile.connect_vendor.ConnectPageTab
import presentation.profile.connect_vendor.ConnectedVendorDetailsPage
import presentation.profile.UserOrders.UserOrders
import presentation.consultation.ConsultationScreen
import presentation.consultation.VirtualConsultationRoom
import presentation.dialogs.LoadingDialog
import presentation.main.account.JoinASpa
import presentation.main.home.HomepagePresenter
import presentation.therapist.TherapistDashboardTab
import presentation.viewmodels.HomePageViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel

class MainScreen(val platformNavigator: PlatformNavigator? = null) : Screen, KoinComponent {

    private var mainViewModel: MainViewModel? = null
    private val preferenceSettings: Settings = Settings()
    private val homepagePresenter: HomepagePresenter by inject()
    private var uiStateViewModel: UIStateViewModel? = null
    private var homePageViewModel: HomePageViewModel? = null
    private var userEmail: String = ""

    @Composable
    override fun Content() {

   userEmail = preferenceSettings["userEmail", ""] // User Email Retrieved After Authentication to populate the System
   val imageUploading = remember { mutableStateOf(false) }

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

         if(imageUploading.value) {
            Box(modifier = Modifier.fillMaxWidth(0.80f)) {
                LoadingDialog("Uploading...")
            }
        }

        if (uiStateViewModel == null) {
            uiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (homePageViewModel == null) {
            homePageViewModel = kmpViewModel(
                factory = viewModelFactory {
                    HomePageViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
            homepagePresenter.getUserHomepage(userEmail)
        }

        val screenNav: State<Pair<Int, Int>>? = mainViewModel?.screenNav?.collectAsState()

        TabNavigator(showDefaultTab(mainViewModel!!, homePageViewModel!!, uiStateViewModel!!, homepagePresenter)) {
            when (screenNav?.value?.second) {
                Screens.MAIN_TAB.toPath() -> {
                    it.current = MainTab(mainViewModel!!, homePageViewModel!!, uiStateViewModel!!, homepagePresenter)
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
                    it.current = UserOrders(mainViewModel!!)
                }
                Screens.CONNECT_VENDOR_PAGE.toPath() -> {
                    it.current = ConnectPageTab(mainViewModel!!, platformNavigator)
                }
                Screens.CONSULTATION_ROOM.toPath() -> {
                    it.current = VirtualConsultationRoom(mainViewModel!!)
                }
                Screens.EDIT_PROFILE.toPath() -> {
                    it.current = EditProfile(mainViewModel!!, platformNavigator)
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
            }

            Scaffold(
                content = { CurrentTab() })
        }
    }

    fun setImageUploadResponse(imageUrl: String) {
        preferenceSettings["imageUrl"] = imageUrl
    }
   fun setImageUploadProcessing(isDone: Boolean = false) {
        preferenceSettings["imageUploadProcessing"] = isDone
    }

    private fun showDefaultTab(mainViewModel: MainViewModel, homePageViewModel: HomePageViewModel,
                               uiStateViewModel: UIStateViewModel, homepagePresenter: HomepagePresenter): MainTab {
        return  MainTab(mainViewModel, homePageViewModel, uiStateViewModel, homepagePresenter)
    }

}
