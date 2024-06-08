package presentation.appointments

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
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Models.Appointment
import domain.Models.AppointmentItemUIModel
import domain.Models.PlatformNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.AppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PostponementViewModel
import presentation.viewmodels.ScreenUIStateViewModel
import presentation.viewmodels.ScreenUIStates
import utils.getAppointmentViewHeight
import presentation.widgets.NewAppointmentWidget
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import rememberStackedSnackbarHostState
import theme.Colors

class AppointmentsTab(private val mainViewModel: MainViewModel,
                      private var appointmentResourceListEnvelopeViewModel: AppointmentResourceListEnvelopeViewModel,private val platformNavigator: PlatformNavigator) : Tab, KoinComponent {

    private val appointmentPresenter: AppointmentPresenter by inject()
    private var screenUiStateViewModel: ScreenUIStateViewModel? = null
    private var deleteActionUIStateViewModel: ActionUIStateViewModel? = null
    private var postponeActionUIStateViewModel: ActionUIStateViewModel? = null
    private var availabilityActionUIStateViewModel: ActionUIStateViewModel? = null
    private var joinMeetingActionUIStateViewModel: ActionUIStateViewModel? = null
    private var postponementViewModel: PostponementViewModel? = null


    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Appointments"
            val icon = painterResource("drawable/calender_icon_semi.png")

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {

       val userId = mainViewModel.userId.collectAsState()

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        if (screenUiStateViewModel == null) {
            screenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (deleteActionUIStateViewModel == null) {
            deleteActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (postponeActionUIStateViewModel == null) {
            postponeActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (availabilityActionUIStateViewModel == null) {
            availabilityActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (joinMeetingActionUIStateViewModel == null) {
            joinMeetingActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (postponementViewModel == null) {
            postponementViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PostponementViewModel(savedStateHandle = createSavedStateHandle())
                },
            )

        }

        if (appointmentResourceListEnvelopeViewModel!!.resources.value.isNotEmpty()){
            screenUiStateViewModel!!.switchScreenUIState(ScreenUIStates(contentVisible = true))
        }else {
            if (userId.value != -1) {
                appointmentPresenter.getUserAppointments(userId.value)
            }
        }


        val loadMoreState =
            appointmentResourceListEnvelopeViewModel?.isLoadingMore?.collectAsState()
        val appointmentList = appointmentResourceListEnvelopeViewModel?.resources?.collectAsState()
        val totalAppointmentsCount =
            appointmentResourceListEnvelopeViewModel?.totalItemCount?.collectAsState()
        val displayedAppointmentsCount =
            appointmentResourceListEnvelopeViewModel?.displayedItemCount?.collectAsState()

        val uiState = screenUiStateViewModel!!.uiStateInfo.collectAsState()
        val deleteActionUIStates = deleteActionUIStateViewModel!!.uiStateInfo.collectAsState()
        val postponeActionUIStates = postponeActionUIStateViewModel!!.uiStateInfo.collectAsState()
        val joinMeetingActionUIStates = joinMeetingActionUIStateViewModel!!.uiStateInfo.collectAsState()

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

        if (postponeActionUIStates.value.isLoading) {
            Box(modifier = Modifier.fillMaxWidth()) {
                LoadingDialog("Updating Appointment")
            }
        }
        else if (postponeActionUIStates.value.isSuccess) {
            ShowSnackBar(title = "Successful",
                description = "Your Have Successfully Postponed Your Appointment",
                actionLabel = "",
                duration = StackedSnackbarDuration.Short,
                snackBarType = SnackBarType.SUCCESS,
                stackedSnackBarHostState,
                onActionClick = {})
            appointmentResourceListEnvelopeViewModel!!.clearData(mutableListOf())
            if (userId.value != -1) {
                appointmentPresenter.getUserAppointments(userId.value)
            }
        }
        else if (postponeActionUIStates.value.isFailed) {
            ShowSnackBar(title = "Failed",
                description = postponeActionUIStates.value.errorMessage,
                actionLabel = "",
                duration = StackedSnackbarDuration.Short,
                snackBarType = SnackBarType.SUCCESS,
                stackedSnackBarHostState,
                onActionClick = {})
        }



        if (deleteActionUIStates.value.isLoading) {
            Box(modifier = Modifier.fillMaxWidth()) {
                LoadingDialog("Deleting Appointment")
            }
        }
        else if (deleteActionUIStates.value.isSuccess) {
            ShowSnackBar(title = "Successful",
                description = "Delete Successful",
                actionLabel = "",
                duration = StackedSnackbarDuration.Short,
                snackBarType = SnackBarType.SUCCESS,
                stackedSnackBarHostState,
                onActionClick = {})
            appointmentResourceListEnvelopeViewModel!!.clearData(mutableListOf())
            if (userId.value != -1) {
                appointmentPresenter.getUserAppointments(userId.value)
            }

        }
        else if (deleteActionUIStates.value.isFailed) {
            ShowSnackBar(title = "Failed",
                description = deleteActionUIStates.value.errorMessage,
                actionLabel = "",
                duration = StackedSnackbarDuration.Short,
                snackBarType = SnackBarType.SUCCESS,
                stackedSnackBarHostState,
                onActionClick = {})
        }



        if (joinMeetingActionUIStates.value.isLoading) {
            Box(modifier = Modifier.fillMaxWidth()) {
                LoadingDialog("Joining Meeting")
            }
        }
        else if (joinMeetingActionUIStates.value.isSuccess) {}
        else if (joinMeetingActionUIStates.value.isFailed) {
            ShowSnackBar(title = "Failed",
                description = joinMeetingActionUIStates.value.errorMessage,
                actionLabel = "",
                duration = StackedSnackbarDuration.Short,
                snackBarType = SnackBarType.SUCCESS,
                stackedSnackBarHostState,
                onActionClick = {})
        }


        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {},
            content = {
                val handler = AppointmentsHandler(
                    appointmentResourceListEnvelopeViewModel,
                    screenUiStateViewModel!!,
                    deleteActionUIStateViewModel!!,
                    joinMeetingActionUIStateViewModel!!,
                    availabilityActionUIStateViewModel!!,
                    postponementViewModel!!,
                    appointmentPresenter,
                    onMeetingTokenReady = {
                        platformNavigator.startVideoCall(it)
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
                                    .padding(bottom = 50.dp)
                                    .height(getAppointmentViewHeight(appointmentUIModel.appointmentList).dp),
                                userScrollEnabled = true
                            ) {
                                itemsIndexed(items = appointmentUIModel.appointmentList) { it, item ->
                                    NewAppointmentWidget(
                                        item,
                                        appointmentPresenter,
                                        postponementViewModel!!
                                    )
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
                                            if (appointmentResourceListEnvelopeViewModel!!.nextPageUrl.value.isNotEmpty()) {
                                                if (userId.value != -1) {
                                                    appointmentPresenter.getMoreAppointments(
                                                        userId.value,
                                                        nextPage = appointmentResourceListEnvelopeViewModel!!.currentPage.value + 1
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
            })
    }
}
