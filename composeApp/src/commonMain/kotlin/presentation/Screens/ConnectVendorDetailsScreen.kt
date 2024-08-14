package presentation.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import applications.device.deviceInfo
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import domain.Models.PlatformNavigator
import domain.Models.Vendor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.LoadingScreenUIStateViewModel
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.russhwolf.settings.set
import domain.Enums.CustomerMovementEnum
import domain.Enums.DeviceType
import domain.Enums.SharedPreferenceEnum
import kotlinx.serialization.Transient
import presentation.DomainViewHandler.VendorInfoPageHandler
import presentation.connectVendor.ConnectVendorPresenter
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.widgets.BusinessInfoContent
import presentation.widgets.BusinessInfoTitle
import utils.ParcelableScreen

@Parcelize
class ConnectVendorDetailsScreen(val vendor: Vendor,val  platformNavigator: PlatformNavigator) : ParcelableScreen, KoinComponent {

   @Transient private val preferenceSettings: Settings = Settings()
   @Transient private val connectVendorPresenter: ConnectVendorPresenter by inject()
   @Transient private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
   @Transient private var actionPerformedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
   @Transient private var mainViewModel: MainViewModel? = null

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }

    override val key: ScreenKey
        get() = "vendorDetailScreen"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val vendorConnected = remember { mutableStateOf(false) }
        val userId = preferenceSettings[SharedPreferenceEnum.PROFILE_ID.toPath(), -1L]
        val userFirstname = preferenceSettings[SharedPreferenceEnum.FIRSTNAME.toPath(),""]
        val connectVendorAction = actionPerformedActionUIStateViewModel!!.uiStateInfo.collectAsState()

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }


        if (loadingScreenUiStateViewModel == null) {
            loadingScreenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    LoadingScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (actionPerformedActionUIStateViewModel == null) {
            actionPerformedActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        // View Contract Handler Initialisation
        val handler = VendorInfoPageHandler(
            loadingScreenUiStateViewModel!!,
            actionPerformedActionUIStateViewModel!!,
            connectVendorPresenter)
        handler.init()

        if (connectVendorAction.value.isLoading) {
            Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                LoadingDialog("Connecting Vendor")
            }
        } else if (connectVendorAction.value.isSuccess) {
            preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath()] = vendor.vendorId
            preferenceSettings[SharedPreferenceEnum.VENDOR_WHATSAPP_PHONE.toPath()] = vendor.whatsAppPhone
            vendorConnected.value = true

            if (deviceInfo() == DeviceType.IOS.toPath()) {
                platformNavigator.goToMainScreen()
            }
            else {
                val mainScreen = MainScreen(platformNavigator)
                mainScreen.setMainViewModel(mainViewModel!!)
                navigator.replaceAll(mainScreen)
            }
        }
        else if (connectVendorAction.value.isFailed){
            // error occurred
        }



        Scaffold(
            topBar = {
                BusinessInfoTitle(onBackPressed = {
                    mainViewModel!!.setOnBackPressed(true)
                })
            },
            content = {
                BusinessInfoContent(vendor) {
                    if (userId != -1L) {
                        connectVendorPresenter.connectVendor(userId, vendor.vendorId!!, action = CustomerMovementEnum.Entry.toPath(),
                            userFirstname = userFirstname)
                    }
                }
            },
            backgroundColor = Color.Transparent,
        )
    }
}
