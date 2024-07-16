package presentation.connectVendor

import UIStates.ActionUIStates
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import domain.Enums.CustomerMovementEnum
import domain.Enums.Screens
import domain.Models.PlatformNavigator
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.ProfileHandler
import presentation.DomainViewHandler.SwitchVendorHandler
import presentation.Splashscreen.SplashScreen
import presentation.components.IndeterminateCircularProgressBar
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.dialogs.SuccessDialog
import presentation.main.MainScreen
import presentation.profile.ProfilePresenter
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.MainViewModel

@Parcelize
class SwitchVendorDetailsTab() : Tab, KoinComponent, Parcelable {
    @Transient
    private var actionUIStateViewModel: ActionUIStateViewModel? = null
    @Transient
    private val profilePresenter: ProfilePresenter by inject()
    @Transient
    private val preferenceSettings: Settings = Settings()
    @Transient
    private var mainViewModel: MainViewModel? = null
    override val options: TabOptions
        @Composable
        get() {
            val title = "Switch Vendor"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {
        if (actionUIStateViewModel == null) {
            actionUIStateViewModel= kmpViewModel(
                factory = viewModelFactory {
                    ActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        val handler = SwitchVendorHandler(profilePresenter,
            actionUIStateViewModel!!)
        handler.init()

        val switchVendorId = mainViewModel!!.switchVendorId.value
        val switchVendorReason = mainViewModel!!.switchVendorReason.value
        val userInfo = mainViewModel!!.currentUserInfo.value
        val uiState = actionUIStateViewModel!!.switchVendorUiState.collectAsState()
        actionUIStateViewModel!!.switchVendorActionUIState(ActionUIStates(isDefault = true))
        var switchVendorSuccess = remember { mutableStateOf(false) }


        Scaffold(
            topBar = {
                BusinessInfoTitle(mainViewModel = mainViewModel)
            },
            content = {

                if (uiState.value.isLoading) {
                    LoadingDialog("Connecting New Vendor")
                } else if (uiState.value.isSuccess) {
                    preferenceSettings["whatsappPhone"] = mainViewModel!!.switchVendor.value.whatsAppPhone
                    mainViewModel!!.setRestartApp(true)
                    actionUIStateViewModel!!.switchVendorActionUIState(ActionUIStates(isDefault = true))
                    mainViewModel!!.setScreenNav(Pair(Screens.VENDOR_INFO.toPath(), Screens.MAIN_TAB.toPath()))
                } else if (uiState.value.isFailed) {
                    ErrorDialog(dialogTitle = "Error Occurred Please Try Again", actionTitle = "Retry"){}
                }

                BusinessInfoContent(mainViewModel!!.connectedVendor.value){
                 profilePresenter.switchVendor(userId = userInfo.userId!!,
                     vendorId = switchVendorId, action = CustomerMovementEnum.Exit.toPath(),
                     exitReason = switchVendorReason)
                }
            },
            backgroundColor = Color.Transparent
        )
    }
}
