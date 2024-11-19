package presentation.profile

import UIStates.AppUIStates
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import applications.device.deviceInfo
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import domain.Enums.DeviceType
import domain.Models.PlatformNavigator
import domain.Models.Vendor
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.ProfileHandler
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.BusinessInfoContent
import presentation.widgets.VendorDetailsTitle

@Parcelize
class JoinSpaDetailsTab(val platformNavigator: PlatformNavigator) : Tab, KoinComponent, Parcelable {
    @Transient
    private var performedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
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
        if (performedActionUIStateViewModel == null) {
            performedActionUIStateViewModel= kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        val profileHandler = ProfileHandler(profilePresenter,
            onUserLocationReady = {},
            onVendorInfoReady = { it -> },
            performedActionUIStateViewModel!!)
        profileHandler.init()


        val userInfo = mainViewModel!!.currentUserInfo.value
        val joinSpaUiState = performedActionUIStateViewModel!!.uiStateInfo.collectAsState()


        Scaffold(
            topBar = {
                VendorDetailsTitle(onBackPressed = {

                })
            },
            content = {
                val joinVendor = mainViewModel!!.joinSpaVendor.value
                if (joinSpaUiState.value.isLoading) {
                    LoadingDialog("Joining Spa")
                } else if (joinSpaUiState.value.isSuccess) {
                    performedActionUIStateViewModel!!.switchActionUIState(AppUIStates(isDefault = true))
                    mainViewModel!!.setSwitchVendor(vendor = Vendor())
                    mainViewModel!!.setJoinSpaVendor(vendor = Vendor())

                } else if (joinSpaUiState.value.isFailed) {
                    ErrorDialog(dialogTitle = "Error Occurred Please Try Again", actionTitle = "Retry"){}
                }

                BusinessInfoContent(joinVendor){
                    profilePresenter.joinSpa(therapistId = userInfo.userId!!, vendorId = joinVendor.vendorId!!)
                }
            },
            backgroundColor = Color.Transparent
        )
    }
}
