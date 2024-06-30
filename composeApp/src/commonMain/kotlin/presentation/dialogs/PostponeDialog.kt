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
import domain.Models.AvailableTime
import presentation.appointments.AppointmentPresenter
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.PostponementViewModel
import presentation.widgets.NewDateContent
import presentation.widgets.TimeGrid
import presentation.widgets.TitleWidget
import presentation.widgets.buttonContent
import presentations.components.TextComponent

@Composable
fun PostponeDialog(appointment: Appointment,appointmentPresenter: AppointmentPresenter,postponementViewModel: PostponementViewModel,
                  availabilityActionUIStateViewModel: ActionUIStateViewModel, onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit) {
    Dialog( properties = DialogProperties(usePlatformDefaultWidth = false), onDismissRequest = { onDismissRequest() }) {

        val uiState = postponementViewModel.postponementViewUIState.collectAsState()
        val newSelectedDay = postponementViewModel.day.collectAsState()
        val newSelectedMonth = postponementViewModel.month.collectAsState()
        val newSelectedYear = postponementViewModel.year.collectAsState()
        val newSelectedTime = postponementViewModel.selectedTime.collectAsState()
        val therapistBookedTimes = postponementViewModel.therapistBookedTimes.value
        val therapistId = postponementViewModel.currentAppointment.value.therapistId
        val isNewDateSelected = remember { mutableStateOf(true) }
        val availabilityUIStates = availabilityActionUIStateViewModel.availabilityStateInfo.collectAsState()


        appointmentPresenter.getTherapistAvailability(
            therapistId,
            day = newSelectedDay.value,
            month = newSelectedMonth.value,
            year = newSelectedYear.value
        )

        if (isNewDateSelected.value) {
            appointmentPresenter.getTherapistAvailability(
                therapistId,
                day = newSelectedDay.value,
                month = newSelectedMonth.value,
                year = newSelectedYear.value
            )
            isNewDateSelected.value = false
        }


        val normalisedBookedTimes = arrayListOf<PlatformTime>()
        val bookedTimes = arrayListOf<AvailableTime>()
        val displayTimes = remember { mutableStateOf(arrayListOf<AvailableTime>()) }
        val normalisedTherapistTimes = arrayListOf<AvailableTime>()
        for (item in therapistBookedTimes) {
            normalisedBookedTimes.add(item.platformTime!!)
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

                        if (availabilityUIStates.value.isLoading) {
                            Box(
                                modifier = Modifier.fillMaxWidth().height(60.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                IndeterminateCircularProgressBar()
                            }
                        }
                        else if (availabilityUIStates.value.isSuccess) {
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
                                })
                            }
                        }
                        else {
                            // onDismissRequest()
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
