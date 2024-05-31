package presentation.dialogs

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import domain.Models.Appointment
import domain.Models.PlatformTime
import domain.Models.ServiceTime
import presentation.appointments.AppointmentPresenter
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.PostponementViewModel
import presentation.widgets.NewDateContent
import presentation.widgets.TimeGrid
import presentation.widgets.TitleWidget
import presentation.widgets.buttonContent
import presentations.components.TextComponent

@Composable
fun PostponeDialog(appointment: Appointment,appointmentPresenter: AppointmentPresenter,postponementViewModel: PostponementViewModel, onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit) {
    Dialog( properties = DialogProperties(usePlatformDefaultWidth = false), onDismissRequest = { onDismissRequest() }) {

        val uiState = postponementViewModel.postponementViewUIState.collectAsState()
        val serviceTimes = postponementViewModel.therapistAvailableTimes.collectAsState()
        val newSelectedDay = postponementViewModel.day.collectAsState()
        val newSelectedMonth = postponementViewModel.month.collectAsState()
        val newSelectedYear = postponementViewModel.year.collectAsState()
        val newSelectedTime = postponementViewModel.selectedTime.collectAsState()
        val therapistBookedTimes = postponementViewModel.therapistBookedTimes.value
        val timeOffs = postponementViewModel.therapistTimeOffs.collectAsState()
        val specialistId = postponementViewModel.currentAppointment.value.specialistId
        val isNewDateSelected = remember { mutableStateOf(true) }

        if (isNewDateSelected.value) {
            appointmentPresenter.getTherapistAvailability(
                specialistId,
                day = newSelectedDay.value,
                month = newSelectedMonth.value,
                year = newSelectedYear.value
            )
            isNewDateSelected.value = false
        }


        val normalisedBookedTimes = arrayListOf<PlatformTime>()
        val normalisedTimeOffTimes = arrayListOf<PlatformTime>()
        val bookedTimes = arrayListOf<ServiceTime>()
        val displayTimes = remember { mutableStateOf(arrayListOf<ServiceTime>()) }
        for (item in timeOffs.value){
            normalisedTimeOffTimes.add(item.timeOffTime?.platformTime!!)
        }

        val normalisedTherapistTimes = arrayListOf<ServiceTime>()
        for (item in therapistBookedTimes) {
            normalisedBookedTimes.add(item.serviceTime?.platformTime!!)
        }

        if (serviceTimes.value.isNotEmpty()) {
            serviceTimes.value.map { it ->
                if (it.platformTime in normalisedBookedTimes || it.platformTime in normalisedTimeOffTimes) {
                    val bookedTime = serviceTimes.value.find { it2 ->
                        it.id == it2.id
                    }?.copy(isAvailable = false)
                    normalisedTherapistTimes.add(bookedTime!!)
                } else {
                    val isAvailableTime =  it.copy(isAvailable = true)
                    normalisedTherapistTimes.add(isAvailableTime)
                }
            }
         }
        if (normalisedTherapistTimes.isNotEmpty()) {
            displayTimes.value = normalisedTherapistTimes
        }


        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Colors.lighterPrimaryColor,
            modifier = Modifier.fillMaxWidth(0.90f)
        ) {
               Card(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    shape = RoundedCornerShape(10.dp),
                    elevation = 15.dp, border = BorderStroke((0.5).dp, color = Colors.primaryColor)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {

                        Box(modifier = Modifier.fillMaxWidth().height(90.dp).background(color = Colors.primaryColor), contentAlignment = Alignment.Center) {
                            TitleWidget(title = "Postpone Service", textColor = Color.White)
                        }

                        NewDateContent(onDateSelected = {
                           postponementViewModel.setSelectedDay(it.dayOfMonth)
                           postponementViewModel.setSelectedYear(it.year)
                           postponementViewModel.setSelectedMonth(it.monthNumber)
                           postponementViewModel.clearServiceTimes()
                           isNewDateSelected.value = true
                        })

                        if (uiState.value.isLoading) {
                            Box(
                                modifier = Modifier.fillMaxWidth().height(60.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                IndeterminateCircularProgressBar()
                            }
                        }
                        else if (uiState.value.isSuccess) {
                            Column(
                                modifier = Modifier
                                    .padding(top = 5.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                                    .fillMaxWidth()
                                    .height(320.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment  = Alignment.CenterHorizontally,
                            ) {

                                TextComponent(
                                    text = "Available Time",
                                    fontSize = 18,
                                    fontFamily = GGSansSemiBold,
                                    textStyle = TextStyle(),
                                    textColor = Colors.darkPrimary,
                                    textAlign = TextAlign.Left,
                                    fontWeight = FontWeight.Black,
                                    textModifier = Modifier.fillMaxWidth().padding(start = 10.dp, bottom = 20.dp)
                                )

                                TimeGrid(displayTimes.value,onWorkHourClickListener = {
                                    postponementViewModel.setNewSelectedTime(it)
                                   // postponementViewModel.clearServiceTimes()
                                })
                            }
                        }
                        else {
                             // Error Occurred
                        }

                        buttonContent(onDismissRequest = {
                            postponementViewModel.clearPostponementSelection()
                            onDismissRequest()
                        }, onConfirmation = {
                          if (newSelectedTime.value.id != null) {
                                appointmentPresenter.postponeAppointment(
                                    appointment,
                                    newSelectedTime.value.id!!,
                                    day = newSelectedDay.value,
                                    month = newSelectedMonth.value,
                                    year = newSelectedYear.value
                                )
                              postponementViewModel.clearPostponementSelection()
                              onConfirmation()
                            }
                        })
                    }
                }

            }
    }
}
