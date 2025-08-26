package presentation.therapist

import StackedSnackbarHost
import UIStates.AppUIStates
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
import androidx.compose.material3.MaterialTheme
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
import com.assignment.moniepointtest.ui.theme.AppTheme
import dev.materii.pullrefresh.PullRefreshIndicator
import dev.materii.pullrefresh.PullRefreshLayout
import dev.materii.pullrefresh.rememberPullRefreshState
import domain.Enums.BookingStatus
import domain.Models.Appointment
import domain.Models.TherapistAppointmentItemUIModel
import domain.Models.TherapistInfo
import presentation.widgets.ErrorOccurredWidget
import presentation.DomainViewHandler.TherapistHandler
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.TherapistAppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.widgets.DropDownWidget
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


    val filterList = arrayListOf<String>()
    filterList.add(BookingStatus.ALL.toPath())
    filterList.add(BookingStatus.PENDING.toPath())
    filterList.add(BookingStatus.POSTPONED.toPath())
    filterList.add(BookingStatus.CANCELLED.toPath())
    filterList.add(BookingStatus.DONE.toPath())

    val currentFilter = remember { mutableStateOf(BookingStatus.ALL.toPath()) }

    val isRefreshing = remember { mutableStateOf(false) }
    val refreshActionUIStates = remember { mutableStateOf(AppUIStates()) }

    if (refreshActionUIStates.value.isLoading){
        isRefreshing.value = true
    }
    else if(refreshActionUIStates.value.isSuccess){
        isRefreshing.value = false
    }
    else if (refreshActionUIStates.value.isFailed){
        isRefreshing.value = false
    }

    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )

    LaunchedEffect(true) {
        if (appointmentResourceListEnvelopeViewModel!!.resources.value.isEmpty()) {
            therapistPresenter.getTherapistAppointments(therapistInfo.id!!)
        }
    }

    val handler = TherapistHandler(therapistPresenter,
        loadingScreenUiStateViewModel = loadingScreenUiStateViewModel,
        performedActionUIStateViewModel,
        onShowRefreshing = {
            refreshActionUIStates.value = it
        },
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


    AppTheme {

        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {},
            backgroundColor = Colors.dashboardBackground,
            content = {

                if (uiState.value.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                } else if (uiState.value.isFailed) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(400.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ErrorOccurredWidget(uiState.value.errorMessage, onRetryClicked = {
                            therapistPresenter.getTherapistAppointments(therapistInfo.id!!)
                        })
                    }
                } else if (uiState.value.isEmpty) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        EmptyContentWidget(emptyText = uiState.value.emptyMessage)
                    }
                } else if (uiState.value.isSuccess) {

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
                                .background(color = Colors.dashboardBackground)
                        ) {

                            attachFilters {
                                currentFilter.value = filterList.get(it)
                                if (it == 0) {
                                    appointmentResourceListEnvelopeViewModel!!.clearData(
                                        mutableListOf()
                                    )
                                    therapistPresenter.getTherapistAppointments(therapistInfo.id!!)
                                } else {
                                    appointmentResourceListEnvelopeViewModel!!.clearData(
                                        mutableListOf()
                                    )
                                    therapistPresenter.getFilteredTherapistAppointments(
                                        therapistId = therapistInfo.id!!,
                                        currentFilter.value
                                    )
                                }
                            }

                            val pullRefreshState = rememberPullRefreshState(
                                refreshing = isRefreshing.value,
                                onRefresh = {
                                    currentFilter.value = BookingStatus.ALL.toPath()
                                    appointmentResourceListEnvelopeViewModel!!.clearData(
                                        mutableListOf()
                                    )
                                    therapistPresenter.refreshTherapistAppointments(therapistInfo.id!!)
                                })
                            PullRefreshLayout(
                                modifier = Modifier.fillMaxSize()
                                    .background(color = Colors.dashboardBackground),
                                state = pullRefreshState,
                                flipped = false,
                                indicator = {
                                    PullRefreshIndicator(
                                        state = pullRefreshState,
                                        backgroundColor = Colors.darkPrimary,
                                        contentColor = Color.White
                                    )
                                }
                            ) {

                                LazyColumn(
                                    modifier = Modifier.fillMaxWidth()
                                        .height(getAppointmentViewHeight(appointmentUIModel.appointmentList.size).dp),
                                    userScrollEnabled = true
                                ) {
                                    itemsIndexed(items = appointmentUIModel.appointmentList) { it, item ->
                                        TherapistDashboardAppointmentWidget(
                                            item,
                                            onArchiveAppointment = {
                                                therapistPresenter.archiveAppointment(it.appointmentId!!)
                                            },
                                            onUpdateToDone = {
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
                                                borderStroke = BorderStroke(
                                                    1.dp,
                                                    Colors.primaryColor
                                                ),
                                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                                                fontSize = 16,
                                                shape = CircleShape,
                                                textColor = Colors.darkPrimary,
                                                style = MaterialTheme.typography.titleMedium
                                            ) {
                                                if (appointmentResourceListEnvelopeViewModel.nextPageUrl.value.isNotEmpty()) {
                                                    if (currentFilter.value == BookingStatus.ALL.toPath()) {
                                                        therapistPresenter.getMoreTherapistAppointments(
                                                            therapistInfo.id!!,
                                                            nextPage = appointmentResourceListEnvelopeViewModel.currentPage.value + 1
                                                        )
                                                    } else {
                                                        therapistPresenter.getMoreFilteredTherapistAppointments(
                                                            therapistInfo.id!!,
                                                            currentFilter.value,
                                                            appointmentResourceListEnvelopeViewModel?.currentPage?.value!! + 1
                                                        )
                                                    }
                                                }
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

}

@Composable
fun attachFilters(onMenuItemClick : (Int) -> Unit){
    val nameList = arrayListOf<String>()
    nameList.add(BookingStatus.ALL.toName())
    nameList.add(BookingStatus.PENDING.toName())
    nameList.add(BookingStatus.POSTPONED.toName())
    nameList.add(BookingStatus.CANCELLED.toName())
    nameList.add(BookingStatus.DONE.toName())

    DropDownWidget(menuItems = nameList, selectedIndex = 0,iconRes = "drawable/urban_icon.png", placeHolderText = "Select Filter", onMenuItemClick = {
        onMenuItemClick(it)
    }, onExpandMenuItemClick = {

    })
}