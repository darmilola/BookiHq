package presentation.therapist

import StackedSnackbarHost
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import domain.Models.Appointment
import domain.Models.AppointmentItemUIModel
import presentation.appointments.AppointmentPresenter
import presentation.appointments.AppointmentsHandler
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.AppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PostponementViewModel
import presentation.viewmodels.ResourceListEnvelopeViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.NewAppointmentWidget
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentation.widgets.TherapistAppointmentWidget
import rememberStackedSnackbarHostState
import theme.Colors
import utils.getAppointmentViewHeight

@Composable
fun TherapistAppointment(mainViewModel: MainViewModel,uiStateViewModel: UIStateViewModel, postponementViewModel: PostponementViewModel,
                         appointmentResourceListEnvelopeViewModel: AppointmentResourceListEnvelopeViewModel?, appointmentPresenter: AppointmentPresenter) {

    val currentUser = mainViewModel.currentUserInfo.value
    val specialistInfo = mainViewModel.currentSpecialistInfo.value

    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )

    val postponeAppointmentProgress = remember { mutableStateOf(false) }
    val postponeAppointmentSuccess = remember { mutableStateOf(false) }
    val postponeAppointmentFailed = remember { mutableStateOf(false) }

    val deleteAppointmentProgress = remember { mutableStateOf(false) }
    val deleteAppointmentSuccess = remember { mutableStateOf(false) }
    val deleteAppointmentFailed = remember { mutableStateOf(false) }

    LaunchedEffect(Unit, block = {
        appointmentPresenter.getSpecialistAppointments(7)
    })

    val loadMoreState =
        appointmentResourceListEnvelopeViewModel?.isLoadingMore?.collectAsState()
    val appointmentList = appointmentResourceListEnvelopeViewModel?.resources?.collectAsState()
    val totalAppointmentsCount =
        appointmentResourceListEnvelopeViewModel?.totalItemCount?.collectAsState()
    val displayedAppointmentsCount =
        appointmentResourceListEnvelopeViewModel?.displayedItemCount?.collectAsState()
    val uiState = uiStateViewModel.uiData.collectAsState()
    val lastIndex = appointmentList?.value?.size?.minus(1)
    val selectedAppointment = remember { mutableStateOf(Appointment()) }


    var appointmentUIModel by remember {
        mutableStateOf(
            AppointmentItemUIModel(
                selectedAppointment.value,
                appointmentList?.value!!
            )
        )
    }

    if (!loadMoreState?.value!!) {
        appointmentUIModel =
            appointmentUIModel.copy(selectedAppointment = selectedAppointment.value,
                appointmentList = appointmentResourceListEnvelopeViewModel!!.resources.value.map { it2 ->
                    it2.copy(
                        isSelected = it2.appointmentId == selectedAppointment.value.appointmentId
                    )
                })
         }

    Scaffold(
        snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
        topBar = {},
        content = {
            val handler = AppointmentsHandler(
                appointmentResourceListEnvelopeViewModel,
                uiStateViewModel,
                postponementViewModel,
                appointmentPresenter,
                onPostponeAppointment = {
                    postponeAppointmentProgress.value = true
                },
                onPostponeDone = {
                    postponeAppointmentProgress.value = false
                    postponeAppointmentSuccess.value = true
                },
                onPostponeFailed = {
                    postponeAppointmentProgress.value = false
                    postponeAppointmentFailed.value = true
                },
                onDeleteSuccess = {
                    deleteAppointmentProgress.value = false
                    deleteAppointmentSuccess.value = true
                }, onDeleteFailed = {
                    deleteAppointmentFailed.value = true
                },
                onDeleteStarted = {
                    deleteAppointmentProgress.value = true
                }
            )
            handler.init()



            if (uiState.value.loadingVisible) {
                //Content Loading
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    IndeterminateCircularProgressBar()
                }
            } else if (uiState.value.errorOccurred) {

                //Error Occurred display reload

            } else if (uiState.value.contentVisible) {

                val columnModifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()

                if (postponeAppointmentProgress.value) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        LoadingDialog("Updating Appointment")
                    }
                }
                else if (postponeAppointmentSuccess.value) {
                    ShowSnackBar(title = "Successful",
                        description = "Your Have Successfully Postponed Your Appointment",
                        actionLabel = "",
                        duration = StackedSnackbarDuration.Short,
                        snackBarType = SnackBarType.SUCCESS,
                        stackedSnackBarHostState,
                        onActionClick = {})
                    postponeAppointmentSuccess.value = false
                    appointmentResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                    appointmentPresenter.getUserAppointments(currentUser?.userId!!)

                }

                if (deleteAppointmentProgress.value) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        LoadingDialog("Deleting Appointment")
                    }
                }
                else if (deleteAppointmentSuccess.value) {
                    ShowSnackBar(title = "Successful",
                        description = "Delete Successful",
                        actionLabel = "",
                        duration = StackedSnackbarDuration.Short,
                        snackBarType = SnackBarType.SUCCESS,
                        stackedSnackBarHostState,
                        onActionClick = {})
                    deleteAppointmentSuccess.value = false
                    appointmentResourceListEnvelopeViewModel.clearData(mutableListOf())
                    appointmentPresenter.getUserAppointments(currentUser.userId!!)

                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = columnModifier
                ) {
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .background(color = Color.White)
                    ) {

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                                .height(getAppointmentViewHeight(appointmentUIModel.appointmentList).dp),
                            userScrollEnabled = true
                        ) {
                            itemsIndexed(items = appointmentUIModel.appointmentList) { it, item ->
                                TherapistAppointmentWidget(
                                    item,
                                    appointmentPresenter,
                                    postponementViewModel)
                                if (it == lastIndex && loadMoreState.value) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().height(60.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        IndeterminateCircularProgressBar()
                                    }
                                } else if (it == lastIndex && (displayedAppointmentsCount?.value!! < totalAppointmentsCount?.value!!)) {
                                    val buttonStyle = Modifier
                                        .height(60.dp)
                                        .fillMaxWidth()
                                        .padding(top = 10.dp, start = 10.dp, end = 10.dp)

                                    ButtonComponent(
                                        modifier = buttonStyle,
                                        buttonText = "Show More",
                                        borderStroke = BorderStroke(1.dp, Colors.primaryColor),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                                        fontSize = 16,
                                        shape = CircleShape,
                                        textColor = Colors.primaryColor,
                                        style = TextStyle()
                                    ) {
                                        if (appointmentResourceListEnvelopeViewModel.nextPageUrl.value.isNotEmpty()) {
                                            appointmentPresenter.getMoreSpecialistAppointments(
                                                7,
                                                nextPage = appointmentResourceListEnvelopeViewModel.currentPage.value + 1
                                            )
                                        }
                                    }
                                }
                            }

                        }

                    }
                }

            }
        })

}