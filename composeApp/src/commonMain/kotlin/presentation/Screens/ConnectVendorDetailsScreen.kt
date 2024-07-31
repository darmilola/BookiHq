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
import presentation.viewmodels.UIStateViewModel
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.russhwolf.settings.set
import domain.Enums.CustomerMovementEnum
import kotlinx.serialization.Transient
import presentation.DomainViewHandler.VendorInfoPageHandler
import presentation.connectVendor.ConnectVendorPresenter
import presentation.viewmodels.MainViewModel
import presentation.widgets.BusinessInfoContent
import presentation.widgets.BusinessInfoTitle
import utils.ParcelableScreen

@Parcelize
class ConnectVendorDetailsScreen(val vendor: Vendor,val  platformNavigator: PlatformNavigator? = null) : ParcelableScreen, KoinComponent {

   @Transient private val preferenceSettings: Settings = Settings()
   @Transient private val connectVendorPresenter: ConnectVendorPresenter by inject()
   @Transient private var uiStateViewModel: UIStateViewModel? = null
   @Transient private var mainViewModel: MainViewModel? = null

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val pageVisible = remember { mutableStateOf(false) }
        val pageLoading = remember { mutableStateOf(false) }
        val errorVisible = remember { mutableStateOf(false) }
        val vendorConnected = remember { mutableStateOf(false) }
        val userId = preferenceSettings["profileId", -1L]
        val userFirstname = preferenceSettings["firstname",""]

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            val navigator = LocalNavigator.currentOrThrow
            val connectVendor = ConnectVendorScreen(platformNavigator)
            mainViewModel!!.setOnBackPressed(false)
            connectVendor.setMainViewModel(mainViewModel!!)
            navigator.replaceAll(connectVendor)
        }


        if (uiStateViewModel == null) {
            uiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        // View Contract Handler Initialisation
        val handler = VendorInfoPageHandler(
            null,
            uiStateViewModel!!,
            connectVendorPresenter,
            onPageLoading = {
                pageLoading.value = true
            },
            onContentVisible = {
                pageLoading.value = false
                pageVisible.value = true
            },
            onErrorVisible = {
                errorVisible.value = true
                pageLoading.value = false
            },
            onConnected = {
                preferenceSettings["vendorId"] = vendor.vendorId
                preferenceSettings["whatsappPhone"] = vendor.whatsAppPhone
                vendorConnected.value = true
            })
        handler.init()

        if (pageLoading.value) {
            Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                LoadingDialog("Connecting Vendor")
            }
        } else if (vendorConnected.value) {
            val mainScreen = MainScreen(platformNavigator)
            mainScreen.setMainViewModel(mainViewModel!!)
            navigator.replaceAll(mainScreen)
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
                            userFirstname = userFirstname, vendor = vendor, platformNavigator = platformNavigator!!)
                    }
                }
            },
            backgroundColor = Color.Transparent,
        )
    }
}
