package presentation.connectVendor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
import presentations.components.ImageComponent
import presentations.components.TextComponent
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
                BusinessInfoContent(vendor)
            },
            backgroundColor = Colors.dashboardBackground,
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.wrapContentSize(),
                    contentColor = Colors.darkPrimary,
                    containerColor = Colors.darkPrimary,
                    shape = RoundedCornerShape(15.dp),
                    onClick = {
                        connectVendorPresenter.connectVendor(userId, vendor.vendorId!!, action = CustomerMovementEnum.Entry.toPath())
                    }) {
                    floatingButton()
                }
            }
        )
    }
        }

    @Composable
    fun floatingButton(){
        Row(
            modifier = Modifier.height(45.dp).width(150.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
        ) {

            TextComponent(
                textModifier = Modifier.wrapContentWidth()
                    .padding(start = 5.dp),
                text = "Connect",
                fontSize = 16,
                textStyle = MaterialTheme.typography.titleMedium,
                textColor = Color.White,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 23,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            val modifier = Modifier
                .padding(start = 10.dp)
                .size(30.dp)
            ImageComponent(imageModifier = modifier, imageRes = "drawable/forward_arrow.png", colorFilter = ColorFilter.tint(color = Color.White))



        }

    }
}

