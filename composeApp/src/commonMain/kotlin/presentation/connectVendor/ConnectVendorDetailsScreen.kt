package presentation.connectVendor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.assignment.moniepointtest.ui.theme.AppTheme
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
import domain.Enums.SharedPreferenceEnum
import kotlinx.serialization.Transient
import presentation.DomainViewHandler.VendorInfoPageHandler
import presentation.Screens.MainScreen
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.widgets.BusinessInfoContent
import presentation.widgets.VendorDetailsTitle
import theme.Colors
import utils.ParcelableScreen

@Parcelize
class ConnectVendorDetailsScreen(val vendor: Vendor,val  platformNavigator: PlatformNavigator) : ParcelableScreen, KoinComponent {

   @Transient private val preferenceSettings: Settings = Settings()
   @Transient private val connectVendorPresenter: ConnectVendorPresenter by inject()
   @Transient private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
   @Transient private var connectVendorActionUIStateViewModel: PerformedActionUIStateViewModel? = null
   @Transient private var mainViewModel: MainViewModel? = null
   @Transient private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    fun setMainViewModel(mainViewModel: MainViewModel) {
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {

        if (loadingScreenUiStateViewModel == null) {
            loadingScreenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    LoadingScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (connectVendorActionUIStateViewModel == null) {
            connectVendorActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        // View Contract Handler Initialisation
        val handler = VendorInfoPageHandler(
            loadingScreenUiStateViewModel!!,
            connectVendorActionUIStateViewModel!!,
            connectVendorPresenter)
        handler.init()

        val navigator = LocalNavigator.currentOrThrow
        val userId = preferenceSettings[SharedPreferenceEnum.USER_ID.toPath(), -1L]
        val connectVendorAction = connectVendorActionUIStateViewModel!!.uiStateInfo.collectAsState()

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }


        if (connectVendorAction.value.isLoading) {
            Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                LoadingDialog("Connecting Vendor")
            }
        } else if (connectVendorAction.value.isSuccess) {
            connectVendorAction.value.isSuccess = false
            mainViewModel!!.setConnectedVendor(vendor)
            preferenceSettings[SharedPreferenceEnum.VENDOR_ID.toPath()] = vendor.vendorId
            val mainScreen = MainScreen(platformNavigator)
            mainScreen.setMainViewModel(mainViewModel!!)
            mainScreen.setDatabaseBuilder(databaseBuilder)
            navigator.replaceAll(mainScreen)
        }
        else if (connectVendorAction.value.isFailed){}



        AppTheme {
        Scaffold(
            topBar = {
                VendorDetailsTitle(onBackPressed = {
                    mainViewModel!!.setOnBackPressed(true)
                })
            },
            content = {
                BusinessInfoContent(vendor) {
                    if (userId != -1L) {
                        connectVendorPresenter.connectVendor(userId, vendor.vendorId!!, action = CustomerMovementEnum.Entry.toPath())
                    }
                }
            },
            backgroundColor = Colors.dashboardBackground,
        )
    }
        }
}
