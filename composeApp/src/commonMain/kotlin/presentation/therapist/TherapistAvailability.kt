package presentation.therapist

import GGSansSemiBold
import StackedSnackbarHost
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.Models.PlatformTime
import domain.Models.AvailableTime
import presentation.components.IndeterminateCircularProgressBar
import presentation.dataModeller.CalendarDataSource
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.TherapistViewModel
import presentation.viewmodels.ScreenUIStateViewModel
import presentation.viewmodels.ScreenUIStates
import presentation.widgets.LinearProgressIndicatorWidget
import presentation.widgets.NewDateContent
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.TherapistAvailabilityTimeGrid
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors

@Composable
fun TherapistAvailability(mainViewModel: MainViewModel, therapistPresenter: TherapistPresenter, therapistViewModel: TherapistViewModel,
                          screenUiStateViewModel: ScreenUIStateViewModel, actionUIStateViewModel: ActionUIStateViewModel){

    val screenUiState = screenUiStateViewModel.uiStateInfo.collectAsState()
    val actionUIState = actionUIStateViewModel.uiStateInfo.collectAsState()

    val newSelectedDay = therapistViewModel.day.collectAsState()
    val newSelectedMonth = therapistViewModel.month.collectAsState()
    val newSelectedYear = therapistViewModel.year.collectAsState()
    val newTimeOffs = therapistViewModel.addedTimeOffs.collectAsState()
    val isNewDateSelected = remember { mutableStateOf(true) }
    val isSaveVisible = remember { mutableStateOf(false) }
    isSaveVisible.value = newTimeOffs.value.isNotEmpty()
    val therapistId = mainViewModel.specialistId.value
    val currentDate = remember { mutableStateOf(CalendarDataSource().today) }


    therapistViewModel.setSelectedDay(day = currentDate.value.dayOfMonth)
    therapistViewModel.setSelectedMonth(month = currentDate.value.monthNumber)
    therapistViewModel.setSelectedYear(year = currentDate.value.year)
    therapistViewModel.clearServiceTimes()


    val availableWorkHour = therapistViewModel.therapistAvailableTimes.collectAsState()
    val timeOffs = therapistViewModel.therapistTimeOffs.collectAsState()
    val normalisedTimeOffTimes = arrayListOf<PlatformTime>()
    val displayTimes = remember { mutableStateOf(arrayListOf<AvailableTime>()) }
    val normalisedBookingTimes = arrayListOf<AvailableTime>()

    if (isNewDateSelected.value){
        therapistPresenter.getTherapistAvailability(therapistId, newSelectedDay.value, newSelectedMonth.value,
            newSelectedYear.value)
    }



    for (item in timeOffs.value) {
        normalisedTimeOffTimes.add(item.timeOffTime?.vendorTime?.platformTime!!)
    }

    availableWorkHour.value.map { it ->
        if (it.vendorTime?.platformTime in normalisedTimeOffTimes){
            val normalisedTime =  availableWorkHour.value.find { it2 ->
                it.vendorTime?.id == it2.vendorTime?.id
            }?.copy(isAvailable = false)
            normalisedBookingTimes.add(normalisedTime!!)
        }
        else{
            val isAvailableTime =  it.copy(isAvailable = true)
            normalisedBookingTimes.add(isAvailableTime)
        }
    }



    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )

    displayTimes.value = normalisedBookingTimes



    Scaffold(
        snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
        topBar = {},
        content = {
            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {

              /*  if (actionUIState.value.contentVisible){
                    ShowSnackBar(title = "Successful",
                        description = "Availability Updated Successfully",
                        actionLabel = "",
                        duration = StackedSnackbarDuration.Short,
                        snackBarType = SnackBarType.SUCCESS,
                        stackedSnackBarHostState,
                        onActionClick = {})
                    actionUIStateViewModel.switchActionUIState(ScreenUIStates())
                }*/

                NewDateContent(onDateSelected = {
                    currentDate.value = it
                    therapistViewModel.setSelectedDay(it.dayOfMonth)
                    therapistViewModel.setSelectedYear(it.year)
                    therapistViewModel.setSelectedMonth(it.monthNumber)
                    therapistViewModel.clearServiceTimes()
                    isNewDateSelected.value = true
                })
                if (screenUiState.value.loadingVisible) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                }
                else if (screenUiState.value.contentVisible){
                    isNewDateSelected.value = false
                    Column(
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .height(350.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment  = Alignment.CenterHorizontally,
                    ) {

                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextComponent(
                                text = "Hours",
                                fontSize = 18,
                                fontFamily = GGSansSemiBold,
                                textStyle = TextStyle(),
                                textColor = Colors.darkPrimary,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Black,
                                textModifier = Modifier.fillMaxWidth(0.50f).padding(start = 10.dp, bottom = 20.dp))
                        }
                       /* if (actionUIState.value.loadingVisible) {
                            Box(
                                modifier = Modifier.fillMaxWidth().height(20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                LinearProgressIndicatorWidget()
                            }
                        }*/

                        TherapistAvailabilityTimeGrid(displayTimes.value, onWorkHourUnAvailable = {
                            therapistPresenter.addTimeOff(specialistId = therapistId, it.id!!, day = newSelectedDay.value, month = newSelectedMonth.value,
                                year = newSelectedYear.value)

                        }, onWorkHourAvailable = {
                            therapistPresenter.removeTimeOff(therapistId, it.id!!,day = newSelectedDay.value, month = newSelectedMonth.value,
                                year = newSelectedYear.value)
                        })
                    }
                }
                else {
                    // Error Occurred
                }
            }
        })






}