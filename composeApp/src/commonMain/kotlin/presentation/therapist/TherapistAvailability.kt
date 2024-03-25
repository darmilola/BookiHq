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
import domain.Models.ServiceTime
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.AsyncUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.TherapistViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.UIStates
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
                          uiStateViewModel: UIStateViewModel, asyncUIStateViewModel: AsyncUIStateViewModel){

    val uiState = uiStateViewModel.uiData.collectAsState()
    val asyncState = asyncUIStateViewModel.uiData.collectAsState()
    val serviceTimes = therapistViewModel.therapistAvailableTimes.collectAsState()
    val timeOffs = therapistViewModel.therapistTimeOffs.collectAsState()
    val newSelectedDate = therapistViewModel.selectedDate.collectAsState()
    val newTimeOffs = therapistViewModel.addedTimeOffs.collectAsState()
    val specialistInfo = mainViewModel.currentSpecialistInfo.value
    val isNewDateSelected = remember { mutableStateOf(true) }
    val isSaveVisible = remember { mutableStateOf(false) }
    isSaveVisible.value = newTimeOffs.value.isNotEmpty()

    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )

    if (isNewDateSelected.value) {
        therapistPresenter.getTherapistAvailability(
            3,
            selectedDate = newSelectedDate.value.toString()
        )
        isNewDateSelected.value = false
    }


    val normalisedTimeOff = arrayListOf<ServiceTime>()
    val displayTimes = remember { mutableStateOf(arrayListOf<ServiceTime>()) }
    for (item in timeOffs.value){
        normalisedTimeOff.add(item.timeOffTime!!)
    }
    val normalisedTherapistTimes = arrayListOf<ServiceTime>()

    if (serviceTimes.value.isNotEmpty()) {
        serviceTimes.value.map { it ->
            if (it in normalisedTimeOff) {
                val timeOffTime = serviceTimes.value.find { it2 ->
                    it.id == it2.id
                }?.copy(isAvailable = false)
                normalisedTherapistTimes.add(timeOffTime!!)
            } else {
                normalisedTherapistTimes.add(it)
            }
        }
    }
    if (normalisedTherapistTimes.isNotEmpty()) {
        displayTimes.value = normalisedTherapistTimes
    }

    Scaffold(
        snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
        topBar = {},
        content = {
            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {

                if (asyncState.value.contentVisible){
                    ShowSnackBar(title = "Successful",
                        description = "Availability Updated Successfully",
                        actionLabel = "",
                        duration = StackedSnackbarDuration.Short,
                        snackBarType = SnackBarType.SUCCESS,
                        stackedSnackBarHostState,
                        onActionClick = {})
                    asyncUIStateViewModel.switchState(UIStates())
                }

                NewDateContent(onDateSelected = {
                    therapistViewModel.setNewSelectedDate(it)
                    therapistViewModel.clearServiceTimes()
                    isNewDateSelected.value = true

                })
                if (uiState.value.loadingVisible) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                }
                else if (uiState.value.contentVisible){
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
                        if (asyncState.value.loadingVisible) {
                            Box(
                                modifier = Modifier.fillMaxWidth().height(20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                LinearProgressIndicatorWidget()
                            }
                        }

                        TherapistAvailabilityTimeGrid(displayTimes.value, onWorkHourUnAvailable = {
                            therapistPresenter.addTimeOff(3, it.id!!, newSelectedDate.value.toString())

                        }, onWorkHourAvailable = {
                            therapistPresenter.removeTimeOff(3, it.id!!, newSelectedDate.value.toString())
                        })
                    }
                }
                else {
                    // Error Occurred
                }
            }
        })






}