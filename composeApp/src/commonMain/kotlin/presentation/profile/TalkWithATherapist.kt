package presentation.profile

import GGSansSemiBold
import StackedSnackbarHost
import UIStates.AppUIStates
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Models.CurrentAppointmentBooking
import domain.Models.PlatformNavigator
import domain.Models.PlatformTime
import domain.Models.VendorTime
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.DomainViewHandler.TalkWithTherapistHandler
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.dialogs.SuccessDialog
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.widgets.BookingCalendar
import presentation.widgets.MultilineInputWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.TitleWidget
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors
import utils.ParcelableScreen
import utils.calculateVendorServiceTimes

@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class TalkWithATherapist(val platformNavigator: PlatformNavigator) : ParcelableScreen, ScreenTransition,
    KoinComponent, Parcelable {

    @Transient
    private val profilePresenter: ProfilePresenter by inject()
    @Transient
    private var performedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient
    private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null

    override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {

        val vendorTimes = remember { mutableStateOf(listOf<VendorTime>()) }
        val platformTimes = remember { mutableStateOf(listOf<PlatformTime>()) }
        val navigator = LocalNavigator.currentOrThrow

        if (performedActionUIStateViewModel == null) {
            performedActionUIStateViewModel= kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }
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


        val currentBooking = CurrentAppointmentBooking()


        val handler = TalkWithTherapistHandler(profilePresenter,
            onAvailableTimesReady = { it1, it2 ->
                vendorTimes.value = it1
                platformTimes.value = it2
            },
            loadingScreenUiStateViewModel = loadingScreenUiStateViewModel!!,
            performedActionUIStateViewModel = performedActionUIStateViewModel!!)
        handler.init()



        val createAppointmentActionUIState = performedActionUIStateViewModel!!.uiStateInfo.collectAsState()
        val loadingScreenUIStates = loadingScreenUiStateViewModel!!.uiStateInfo.collectAsState()



        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 1,
            animation = StackedSnackbarAnimation.Bounce
        )

        profilePresenter.getVendorAvailability(mainViewModel!!.connectedVendor.value.vendorId!!)

        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                Box(modifier = Modifier.fillMaxWidth().height(60.dp)) {
                    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(start = 10.dp), contentAlignment = Alignment.CenterStart) {
                        AttachBackIcon(onBackPressed = {
                            navigator.pop()
                        })
                    }
                    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(), contentAlignment = Alignment.Center) {
                        PageTitle()
                    }
                }
            },
            content = {

                if (loadingScreenUIStates.value.isLoading) {
                    //Content Loading
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                } else if (loadingScreenUIStates.value.isFailed) {

                    //Error Occurred display reload

                } else if (loadingScreenUIStates.value.isSuccess) {


                    if (createAppointmentActionUIState.value.isLoading) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            LoadingDialog("Creating Appointment")
                        }
                    }
                    else if (createAppointmentActionUIState.value.isSuccess) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            SuccessDialog("Appointment Created Successfully", "Close", onConfirmation = {
                                performedActionUIStateViewModel!!.switchActionUIState(AppUIStates(isDefault = true))
                                navigator.pop()
                            })
                        }
                    }
                    else if (createAppointmentActionUIState.value.isFailed) {
                        ErrorDialog("Error Occurred", "Close", onConfirmation = {})
                    }

                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(top = 70.dp, start = 10.dp, end = 10.dp)
                    ) {

                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                        ) {

                            TextComponent(
                                text = "Select Date",
                                fontSize = 16,
                                fontFamily = GGSansSemiBold,
                                textStyle = TextStyle(),
                                textColor = Colors.darkPrimary,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Black,
                                lineHeight = 30,
                                textModifier = Modifier
                                    .fillMaxWidth().padding(start = 10.dp)
                            )

                            BookingCalendar(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 10.dp)) {
                                currentBooking.day = it.dayOfMonth
                                currentBooking.month = it.monthNumber
                                currentBooking.year = it.year
                                currentBooking.monthName = it.month.name
                            }
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
                        ) {

                            val workHours = calculateVendorServiceTimes(platformTimes = platformTimes.value, vendorTimes = vendorTimes.value)

                            VendorTimeContent(workHours, onWorkHourClickListener = {
                                currentBooking.appointmentTime = it
                            })
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                        ) {

                            TextComponent(
                                text = "Reason for consultation?",
                                fontSize = 16,
                                fontFamily = GGSansSemiBold,
                                textStyle = TextStyle(),
                                textColor = Colors.darkPrimary,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Black,
                                lineHeight = 30,
                                textModifier = Modifier
                                    .fillMaxWidth().padding(start = 10.dp)
                            )

                            MultilineInputWidget(viewHeight = 150){
                                currentBooking.description = it
                            }
                        }
                    }
                }


            },
            bottomBar = {
                val buttonStyle = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .height(50.dp)

                ButtonComponent(
                    modifier = buttonStyle,
                    buttonText = "Proceed",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                    fontSize = 18,
                    shape = CircleShape,
                    textColor = Color(color = 0xFFFFFFFF),
                    style = TextStyle(),
                    borderStroke = null
                ) {
                    if (currentBooking.day == -1 || currentBooking.year == -1 || currentBooking.month == -1 || currentBooking.description.isEmpty() || currentBooking.appointmentTime == null){
                        ShowSnackBar(title = "Input Required", description = "Please provide the required info", actionLabel = "", duration = StackedSnackbarDuration.Short, snackBarType = SnackBarType.ERROR,
                            onActionClick = {}, stackedSnackBarHostState = stackedSnackBarHostState)
                    }
                    else {

                    }

                }
            })
    }

    @Composable
    private fun AttachBackIcon(onBackPressed:() -> Unit) {
        PageBackNavWidget {
            onBackPressed()
        }
    }



    @Composable
    fun PageTitle(){
        val rowModifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {
            TitleWidget(title = "Talk With A Therapist", textColor = Colors.primaryColor)
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

@Composable
fun VendorTimeContent(availableHours: ArrayList<PlatformTime>, onWorkHourClickListener: (PlatformTime) -> Unit) {

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 10.dp, top = 15.dp, bottom = 10.dp)
            .height(250.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Available Times",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 10.dp, bottom = 20.dp))
        Row(modifier = Modifier.fillMaxWidth(0.90f).wrapContentHeight()) {
            TextComponent(
                text = "Morning",
                fontSize = 16,
                textStyle = TextStyle(),
                textColor = theme.Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.weight(1f).padding(bottom = 15.dp, top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            TextComponent(
                text = "Afternoon",
                fontSize = 16,
                textStyle = TextStyle(),
                textColor = theme.Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.weight(1f).padding(bottom = 15.dp, top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            TextComponent(
                text = "Evening",
                fontSize = 16,
                textStyle = TextStyle(),
                textColor = theme.Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.weight(1f).padding(bottom = 15.dp, top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
        }

        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            Column(modifier = Modifier.weight(1f).wrapContentHeight()) {}
        }
    }
}





