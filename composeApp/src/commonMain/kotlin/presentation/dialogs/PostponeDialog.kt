package presentation.dialogs

import GGSansSemiBold
import StackedSnackbarHost
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.assignment.moniepointtest.ui.theme.AppTheme
import domain.Models.Appointment
import domain.Models.PlatformTime
import domain.Models.PlatformNavigator
import domain.Models.UserAppointment
import presentation.widgets.ErrorOccurredWidget
import presentation.appointments.AppointmentPresenter
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PostponementViewModel
import presentation.widgets.NewDateContent
import presentation.widgets.TimeGridDisplay
import presentation.widgets.TitleWidget
import presentation.widgets.ButtonContent
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.SubtitleTextWidget
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import utils.calculateTherapistServiceTimes

@Composable
fun PostponeDialog(userAppointment: UserAppointment, appointmentPresenter: AppointmentPresenter,
                   postponementViewModel: PostponementViewModel, mainViewModel: MainViewModel,
                   availabilityPerformedActionUIStateViewModel: PerformedActionUIStateViewModel, onDismissRequest: () -> Unit, platformNavigator: PlatformNavigator,
                   onConfirmation: (Appointment) -> Unit) {
    AppTheme {

        Dialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = { onDismissRequest() }) {

            val newSelectedDay = postponementViewModel.day.collectAsState()
            val newSelectedMonth = postponementViewModel.month.collectAsState()
            val newSelectedYear = postponementViewModel.year.collectAsState()
            val newSelectedTime = postponementViewModel.selectedTime.collectAsState()
            val therapistId = postponementViewModel.currentAppointment.value.resources?.therapistId
            val isNewDateSelected = remember { mutableStateOf(true) }
            val availabilityUIStates =
                availabilityPerformedActionUIStateViewModel.availabilityStateInfo.collectAsState()

            appointmentPresenter.getTherapistAvailability(
                therapistId!!,
                day = newSelectedDay.value,
                month = newSelectedMonth.value,
                year = newSelectedYear.value,
                vendorId = userAppointment.resources?.vendor?.vendorId!!
            )

            if (isNewDateSelected.value) {
                appointmentPresenter.getTherapistAvailability(
                    therapistId,
                    day = newSelectedDay.value,
                    month = newSelectedMonth.value,
                    year = newSelectedYear.value,
                    vendorId = userAppointment.resources.vendor.vendorId
                )
                isNewDateSelected.value = false
            }


            val stackedSnackBarHostState = rememberStackedSnackbarHostState(
                maxStack = 5,
                animation = StackedSnackbarAnimation.Bounce
            )


            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
                snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) }
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Colors.lighterPrimaryColor,
                        modifier = Modifier.fillMaxWidth(0.90f)
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                            shape = RoundedCornerShape(10.dp),
                            elevation = 15.dp,
                            border = BorderStroke((0.5).dp, color = Colors.primaryColor)
                        ) {
                            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().height(90.dp)
                                        .background(color = Colors.primaryColor),
                                    contentAlignment = Alignment.Center
                                ) {
                                    SubtitleTextWidget(text = "Postpone Service", textColor = Color.White)
                                }

                                NewDateContent(mainViewModel = mainViewModel, onDateSelected = {
                                    postponementViewModel.setSelectedDay(it.dayOfMonth)
                                    postponementViewModel.setSelectedYear(it.year)
                                    postponementViewModel.setSelectedMonth(it.monthNumber)
                                    postponementViewModel.setMonthName(it.month.name)
                                    postponementViewModel.clearServiceTimes()
                                    isNewDateSelected.value = true
                                }, onUnAvailableDateSelected = {
                                    ShowSnackBar(title = "Not Available",
                                        description = "Not Available for Service on this Day",
                                        actionLabel = "",
                                        duration = StackedSnackbarDuration.Short,
                                        snackBarType = SnackBarType.INFO,
                                        stackedSnackBarHostState,
                                        onActionClick = {})
                                })

                                if (availabilityUIStates.value.isLoading) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().height(300.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        IndeterminateCircularProgressBar()
                                    }
                                } else if (availabilityUIStates.value.isSuccess) {

                                    val workHours = calculateTherapistServiceTimes(
                                        platformTimes = postponementViewModel.platformTimes.value,
                                        vendorTimes = postponementViewModel.vendorTimes.value,
                                        bookedAppointment = postponementViewModel.therapistBookedTimes.value
                                    )

                                    Column(
                                        modifier = Modifier
                                            .padding(top = 5.dp, bottom = 10.dp)
                                            .fillMaxWidth()
                                            .verticalScroll(rememberScrollState())
                                            .height(300.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        PostponeTimeContent(workHours, onWorkHourClickListener = {
                                            postponementViewModel.setNewSelectedTime(it)
                                        })
                                    }
                                } else if (availabilityUIStates.value.isFailed) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().height(300.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        ErrorOccurredWidget(
                                            availabilityUIStates.value.errorMessage,
                                            onRetryClicked = {
                                                appointmentPresenter.getTherapistAvailability(
                                                    therapistId,
                                                    day = newSelectedDay.value,
                                                    month = newSelectedMonth.value,
                                                    year = newSelectedYear.value,
                                                    vendorId = userAppointment.resources.vendor.vendorId
                                                )
                                            })
                                    }
                                }

                                ButtonContent(onDismissRequest = {
                                    postponementViewModel.clearPostponementSelection()
                                    onDismissRequest()
                                }, onConfirmation = {
                                    if (newSelectedDay.value == -1) {
                                        ShowSnackBar(title = "No Day Selected",
                                            description = "Please Select Appointment Day to proceed",
                                            actionLabel = "",
                                            duration = StackedSnackbarDuration.Short,
                                            snackBarType = SnackBarType.ERROR,
                                            stackedSnackBarHostState,
                                            onActionClick = {})
                                    } else if (newSelectedTime.value.id == null) {
                                        ShowSnackBar(title = "No Time Selected",
                                            description = "Please Select Appointment Time to proceed",
                                            actionLabel = "",
                                            duration = StackedSnackbarDuration.Short,
                                            snackBarType = SnackBarType.ERROR,
                                            stackedSnackBarHostState,
                                            onActionClick = {})
                                    }
                                    if (newSelectedTime.value.id != null && newSelectedDay.value != -1 && newSelectedMonth.value != -1 && newSelectedYear.value != -1) {
                                        appointmentPresenter.postponeAppointment(
                                            userAppointment,
                                            newSelectedTime.value.id!!,
                                            day = newSelectedDay.value,
                                            month = newSelectedMonth.value,
                                            year = newSelectedYear.value,
                                            vendor = userAppointment.resources.vendor,
                                            user = mainViewModel.currentUserInfo.value,
                                            monthName = postponementViewModel.monthName.value,
                                            platformNavigator = platformNavigator,
                                            platformTime = newSelectedTime.value
                                        )
                                        postponementViewModel.clearPostponementSelection()
                                        onConfirmation(userAppointment.resources)
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun PostponeTimeContent(availableHours: Triple<ArrayList<PlatformTime>, ArrayList<PlatformTime>, ArrayList<PlatformTime>>, onWorkHourClickListener: (PlatformTime) -> Unit) {

    Column(
        modifier = Modifier
            .padding(top = 5.dp, bottom = 10.dp)
            .verticalScroll(rememberScrollState())
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Available Times",
            fontSize = 18,
            textStyle = MaterialTheme.typography.titleLarge,
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
                textStyle = MaterialTheme.typography.titleMedium,
                textColor = theme.Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.weight(1f).padding(bottom = 10.dp, top = 5.dp),
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
                textModifier = Modifier.weight(1f).padding(bottom = 10.dp, top = 5.dp),
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
                textModifier = Modifier.weight(1f).padding(bottom = 10.dp, top = 5.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
        }

        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                TimeGridDisplay(platformTimes = availableHours, onWorkHourClickListener = {
                    onWorkHourClickListener(it)
                })
            }
        }
    }
}
