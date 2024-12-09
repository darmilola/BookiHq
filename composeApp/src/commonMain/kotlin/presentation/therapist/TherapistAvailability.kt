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
import domain.Models.AvailableTime
import presentation.components.IndeterminateCircularProgressBar
import presentation.dataModeller.CalendarDataSource
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.TherapistViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.widgets.NewDateContent
import presentation.widgets.TherapistAvailabilityTimeGrid
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors

@Composable
fun TherapistAvailability(mainViewModel: MainViewModel, therapistPresenter: TherapistPresenter, therapistViewModel: TherapistViewModel,
                          loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel, performedActionUIStateViewModel: PerformedActionUIStateViewModel){

    val screenUiState = loadingScreenUiStateViewModel.uiStateInfo.collectAsState()
    val isNewDateSelected = remember { mutableStateOf(true) }
    val isSaveVisible = remember { mutableStateOf(false) }
    val currentDate = remember { mutableStateOf(CalendarDataSource().today) }


    therapistViewModel.setSelectedDay(day = currentDate.value.dayOfMonth)
    therapistViewModel.setSelectedMonth(month = currentDate.value.monthNumber)
    therapistViewModel.setSelectedYear(year = currentDate.value.year)
    therapistViewModel.clearServiceTimes()

    val displayTimes = remember { mutableStateOf(arrayListOf<AvailableTime>()) }
    val normalisedBookingTimes = arrayListOf<AvailableTime>()



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

                if (screenUiState.value.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                }
                else if (screenUiState.value.isSuccess){
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


                        }, onWorkHourAvailable = {

                        })
                    }
                }
                else {
                    // Error Occurred
                }
            }
        })






}