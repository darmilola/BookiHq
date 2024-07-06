package presentation.connectVendor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
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
import presentation.main.MainScreen
import presentation.viewmodels.ScreenUIStateViewModel
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.russhwolf.settings.set
import kotlinx.serialization.Transient
import presentation.DomainViewHandler.VendorInfoPageHandler
import utils.ParcelableScreen

@Parcelize
class ConnectVendorDetailsScreen(val vendor: Vendor, val  platformNavigator: PlatformNavigator? = null) : ParcelableScreen, KoinComponent {

   @Transient private val preferenceSettings: Settings = Settings()
   @Transient private val connectVendorPresenter: ConnectVendorPresenter by inject()
   @Transient private var screenUiStateViewModel: ScreenUIStateViewModel? = null

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val pageVisible = remember { mutableStateOf(false) }
        val pageLoading = remember { mutableStateOf(false) }
        val errorVisible = remember { mutableStateOf(false) }
        val vendorConnected = remember { mutableStateOf(false) }
        val userId = preferenceSettings["profileId", -1L]


        if (screenUiStateViewModel == null) {
            screenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        // View Contract Handler Initialisation
        val handler = VendorInfoPageHandler(
            null,
            screenUiStateViewModel!!,
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
                vendorConnected.value = true
            })
        handler.init()

        if (pageLoading.value) {
            Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                LoadingDialog("Connecting Vendor")
            }
        } else if (vendorConnected.value) {
            navigator.replaceAll(MainScreen(platformNavigator))
        }



        Scaffold(
            topBar = {
                BusinessInfoTitle(onBackPressed = {
                    navigator.replace(ConnectVendorScreen(platformNavigator))
                })
            },
            content = {
                BusinessInfoContent(vendor, isUserAuthenticated = true) {
                    if (userId != -1L) {
                        connectVendorPresenter.connectVendor(userId, vendor.vendorId!!)
                    }
                }
            },
            backgroundColor = Color.Transparent,
        )
    }
}
