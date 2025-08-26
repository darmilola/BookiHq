package presentation.connectVendor

import UIStates.AppUIStates
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.room.RoomDatabase
import applications.room.AppDatabase
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.assignment.moniepointtest.ui.theme.AppTheme
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import domain.Enums.BookingStatus
import domain.Enums.CustomerMovementEnum
import domain.Models.PlatformNavigator
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.SwitchVendorHandler
import presentation.Screens.SplashScreen
import presentation.appointmentBookings.BookingPresenter
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.profile.ProfilePresenter
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.BusinessInfoContent
import presentation.widgets.SwitchVendorBottomSheet
import presentation.widgets.VendorDetailsTitle
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.Colors
import utils.ParcelableScreen

@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class SwitchVendorDetails(val platformNavigator: PlatformNavigator) : ParcelableScreen, KoinComponent,
    ScreenTransition {
    @Transient
    private var performedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient
    private val profilePresenter: ProfilePresenter by inject()
    @Transient private val bookingPresenter: BookingPresenter by inject()
    @Transient
    private var mainViewModel: MainViewModel? = null
    @Transient
    private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null

    override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }
    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        if (performedActionUIStateViewModel == null) {
            performedActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value) {
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }

        val handler = SwitchVendorHandler(
            profilePresenter,
            performedActionUIStateViewModel!!
        )
        handler.init()

        val switchVendorId = mainViewModel!!.switchVendorId.value
        val connectedVendorId = mainViewModel!!.connectedVendor.value.vendorId
        val switchVendorReason = mainViewModel!!.switchVendorReason.value
        val switchVendorValue = mainViewModel!!.switchVendor.value
        val userInfo = mainViewModel!!.currentUserInfo.value
        val switchVendorUiState =
            performedActionUIStateViewModel!!.switchVendorUiState.collectAsState()
        performedActionUIStateViewModel!!.switchVendorActionUIState(AppUIStates(isDefault = true))

        var showSwitchReasonBottomSheet by remember { mutableStateOf(false) }

        if (showSwitchReasonBottomSheet) {
            SwitchVendorBottomSheet(onDismiss = {
                showSwitchReasonBottomSheet = false
            }, onConfirmation = {
                mainViewModel!!.setSwitchVendorReason(it)
                profilePresenter.switchVendor(
                    userId = userInfo.userId!!,
                    vendorId = switchVendorId,
                    action = CustomerMovementEnum.Exit.toPath(),
                    exitReason = switchVendorReason,
                    vendor = mainViewModel!!.connectedVendor.value,
                    platformNavigator = platformNavigator,
                    exitVendorId = connectedVendorId!!
                )
            })
        }


        AppTheme {
            Scaffold(
                topBar = {
                    VendorDetailsTitle(onBackPressed = {
                        navigator.pop()
                    })
                },
                content = {
                    if (switchVendorUiState.value.isLoading) {
                        LoadingDialog("Connecting New Vendor")
                    } else if (switchVendorUiState.value.isSuccess) {
                        mainViewModel!!.setUnsavedOrderSize(0)
                        mainViewModel!!.setCurrentUnsavedOrders(arrayListOf())
                        bookingPresenter.deleteAllPendingAppointment(
                            userId = userInfo.userId!!,
                            bookingStatus = BookingStatus.BOOKING.toPath()
                        )
                        performedActionUIStateViewModel!!.switchVendorActionUIState(
                            AppUIStates(
                                isDefault = true
                            )
                        )
                        mainViewModel!!.setIsSwitchVendor(true)
                        val splashScreen = SplashScreen(platformNavigator = platformNavigator)
                        splashScreen.setDatabaseBuilder(databaseBuilder)
                        splashScreen.setMainViewModel(mainViewModel)
                        navigator.replaceAll(splashScreen)

                    } else if (switchVendorUiState.value.isFailed) {
                        ErrorDialog(
                            dialogTitle = "Error Occurred Please Try Again",
                            actionTitle = "Close"
                        ) {

                        }
                    }
                    BusinessInfoContent(switchVendorValue)
                },
                floatingActionButton = {
                    FloatingActionButton(
                        modifier = Modifier.wrapContentSize(),
                        contentColor = Colors.darkPrimary,
                        containerColor = Colors.darkPrimary,
                        shape = RoundedCornerShape(15.dp),
                        onClick = {
                            showSwitchReasonBottomSheet = true
                        }) {
                        floatingButton()
                    }
                },
                backgroundColor = Colors.dashboardBackground
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
                text = "Switch",
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

    override fun enter(lastEvent: StackEvent): EnterTransition {
        return slideIn { size ->
            val x = if (lastEvent == StackEvent.Pop) -size.width else size.width
            IntOffset(x = x, y = 0)
        }
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        return slideOut { size ->
            val x = if (lastEvent == StackEvent.Pop) size.width else -size.width
            IntOffset(x = x, y = 0)
        }
    }

}
