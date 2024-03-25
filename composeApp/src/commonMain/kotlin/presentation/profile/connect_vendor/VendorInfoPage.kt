package presentation.profile.connect_vendor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import domain.Models.PlatformNavigator
import domain.Models.ResourceListEnvelope
import domain.Models.Vendor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.dialogs.LoadingDialog
import presentation.main.MainScreen
import presentation.viewmodels.ResourceListEnvelopeViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.UIStates

class VendorInfoPage(val vendor: Vendor, val  platformNavigator: PlatformNavigator? = null) : Screen, KoinComponent {

    private val preferenceSettings: Settings = Settings()
    private val connectVendorPresenter: ConnectVendorPresenter by inject()
    private var uiStateViewModel: UIStateViewModel? = null

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val pageVisible = remember { mutableStateOf(false) }
        val pageLoading = remember { mutableStateOf(false) }
        val errorVisible = remember { mutableStateOf(false) }
        val vendorConnected = remember { mutableStateOf(false) }
        val userEmail = preferenceSettings["userEmail",""]


        if (uiStateViewModel == null) {
            uiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        // View Contract Handler Initialisation
        val handler = InfoPageHandler(
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
               vendorConnected.value = true
            })
        handler.init()

        if (pageLoading.value) {
            Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                LoadingDialog("Connecting Vendor")
            }
        }
        else if (vendorConnected.value){
            preferenceSettings["connectedVendor"] = vendor.vendorId
            preferenceSettings["isVendorConnected"] = true
            navigator.replaceAll(MainScreen(platformNavigator))
        }



        Scaffold(
            topBar = {
                BusinessInfoTitle()
            },
            content = {
                BusinessInfoContent(vendor, isUserAuthenticated = true) {
                    if (userEmail.isNotEmpty()) {
                        connectVendorPresenter.connectVendor(userEmail, vendor.vendorId!!)
                    }
                }
            },
            backgroundColor = Color.Transparent,
        )
    }


    class InfoPageHandler(
        private val vendorResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Vendor>? = null,
        private val uiStateViewModel: UIStateViewModel,
        private val connectVendorPresenter: ConnectVendorPresenter,
        private val onPageLoading: () -> Unit,
        private val onContentVisible: () -> Unit,
        private val onConnected: (userEmail: String) -> Unit,
        private val onErrorVisible: () -> Unit
    ) : ConnectVendorContract.View {
        fun init() {
            connectVendorPresenter.registerUIContract(this)
        }

        override fun showLce(uiState: UIStates) {
            uiStateViewModel.switchState(uiState)
            uiState.let {
                when {
                    it.loadingVisible -> {
                        onPageLoading()
                    }

                    it.contentVisible -> {
                        onContentVisible()
                    }

                    it.errorOccurred -> {
                        onErrorVisible()
                    }
                }
            }
        }

        override fun onVendorConnected(userEmail: String) {
            onConnected(userEmail)
        }

        override fun showVendors(vendors: ResourceListEnvelope<Vendor>?, isFromSearch: Boolean) {}

        override fun onLoadMoreVendorStarted(isSuccess: Boolean) {}

        override fun onLoadMoreVendorEnded(isSuccess: Boolean) {}

    }
}
