package presentation.therapist

import StackedSnackbarHost
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import domain.Enums.BookingStatus
import domain.Models.Appointment
import domain.Models.TherapistAppointmentItemUIModel
import domain.Models.TherapistInfo
import drawable.ErrorOccurredWidget
import presentation.DomainViewHandler.TherapistHandler
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.TherapistAppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.widgets.EmptyContentWidget
import presentation.widgets.TherapistDashboardAppointmentWidget
import rememberStackedSnackbarHostState
import theme.Colors
import utils.getAppointmentViewHeight

@Composable
fun TherapistAppointment(mainViewModel: MainViewModel, loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel,
                         appointmentResourceListEnvelopeViewModel: TherapistAppointmentResourceListEnvelopeViewModel?, therapistPresenter: TherapistPresenter,
                         therapistInfo: TherapistInfo,
                         performedActionUIStateViewModel: PerformedActionUIStateViewModel) {


    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )

    LaunchedEffect(true) {
        appointmentResourceListEnvelopeViewModel!!.setResources(mutableListOf())
        appointmentResourceListEnvelopeViewModel.clearData(mutableListOf())
        therapistPresenter.getTherapistAppointments(therapistInfo.id!!)
    }

    val handler = TherapistHandler(therapistPresenter,
        loadingScreenUiStateViewModel = loadingScreenUiStateViewModel,
        performedActionUIStateViewModel,
        onReviewsReady = {},
        onMeetingTokenReady = {},
        appointmentResourceListEnvelopeViewModel!!)
    handler.init()



    val loadMoreState =
        appointmentResourceListEnvelopeViewModel.isLoadingMore.collectAsState()
    val appointmentList = appointmentResourceListEnvelopeViewModel.resources.collectAsState()
    val totalAppointmentsCount =
        appointmentResourceListEnvelopeViewModel.totalItemCount.collectAsState()
    val displayedAppointmentsCount =
        appointmentResourceListEnvelopeViewModel.displayedItemCount.collectAsState()
    val uiState = loadingScreenUiStateViewModel.uiStateInfo.collectAsState()
    val lastIndex = appointmentList.value.size.minus(1)
    val selectedAppointment = remember { mutableStateOf(Appointment()) }


    var appointmentUIModel by remember {
        mutableStateOf(
            TherapistAppointmentItemUIModel(
                selectedAppointment.value,
                appointmentList.value
            )
        )
    }

    if (!loadMoreState.value) {
        appointmentUIModel =
            appointmentUIModel.copy(selectedAppointment = selectedAppointment.value,
                appointmentList = appointmentResourceListEnvelopeViewModel.resources.value)
    }


    Scaffold(
        snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
        topBar = {},
        content = {

            if (uiState.value.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    IndeterminateCircularProgressBar()
                }
            }
            else if (uiState.value.isFailed) {
                Box(modifier = Modifier .fillMaxWidth().height(400.dp), contentAlignment = Alignment.Center) {
                    ErrorOccurredWidget(uiState.value.errorMessage, onRetryClicked = {
                        therapistPresenter.getTherapistAppointments(therapistInfo.id!!)
                    })
                }
            }
            else if (uiState.value.isEmpty) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    EmptyContentWidget(emptyText = uiState.value.emptyMessage)
                }
            }
            else if (uiState.value.isSuccess) {
                val columnModifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()

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
                                .height(getAppointmentViewHeight(appointmentUIModel.appointmentList.size).dp),
                            userScrollEnabled = true
                        ) {
                            itemsIndexed(items = appointmentUIModel.appointmentList) { it, item ->
                                TherapistDashboardAppointmentWidget(item, onArchiveAppointment = {
                                      therapistPresenter.archiveAppointment(it.appointmentId!!)
                                }, onUpdateToDone = {
                                      therapistPresenter.doneAppointment(it.appointmentId!!)
                                })
                                if (it == lastIndex && loadMoreState.value) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().height(60.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        IndeterminateCircularProgressBar()
                                    }
                                } else if (it == lastIndex && (displayedAppointmentsCount.value < totalAppointmentsCount.value)) {
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
                                            therapistPresenter.getMoreTherapistAppointments(
                                                therapistInfo.id!!,
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