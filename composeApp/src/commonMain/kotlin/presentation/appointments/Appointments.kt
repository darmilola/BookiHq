package presentation.appointments

import StackedSnackbarHost
import UIStates.AppUIStates
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Models.AppointmentItemUIModel
import domain.Enums.AppointmentType
import domain.Models.PlatformNavigator
import domain.Models.UserAppointment
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.AppointmentsHandler
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.AppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PostponementViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import dev.materii.pullrefresh.PullRefreshIndicator
import dev.materii.pullrefresh.PullRefreshLayout
import dev.materii.pullrefresh.rememberPullRefreshState
import domain.Enums.SharedPreferenceEnum
import presentation.dialogs.ErrorDialog
import presentation.dialogs.SuccessDialog
import presentation.widgets.MeetingAppointmentWidget
import presentation.widgets.AppointmentWidget
import utils.getAppointmentViewHeight
import rememberStackedSnackbarHostState
import theme.Colors

@Parcelize
class AppointmentsTab(private val platformNavigator: PlatformNavigator) : Tab, KoinComponent, Parcelable {

    private val appointmentPresenter: AppointmentPresenter by inject()
    private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
    private var deletePerformedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    private var postponePerformedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    private var refreshActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    private var postponeTimeUIStateViewModel: PerformedActionUIStateViewModel? = null
    private var joinMeetingPerformedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    private var postponementViewModel: PostponementViewModel? = null
    private var mainViewModel: MainViewModel? = null
    val preferenceSettings = Settings()
    private var appointmentResourceListEnvelopeViewModel: AppointmentResourceListEnvelopeViewModel? = null


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

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {
        if (appointmentResourceListEnvelopeViewModel == null) {
            appointmentResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    AppointmentResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })
        }

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        if (loadingScreenUiStateViewModel == null) {
            loadingScreenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    LoadingScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (deletePerformedActionUIStateViewModel == null) {
            deletePerformedActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (postponePerformedActionUIStateViewModel == null) {
            postponePerformedActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (refreshActionUIStateViewModel == null) {
            refreshActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (postponeTimeUIStateViewModel == null) {
            postponeTimeUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (joinMeetingPerformedActionUIStateViewModel == null) {
            joinMeetingPerformedActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
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

        val loadMoreState =
            appointmentResourceListEnvelopeViewModel?.isLoadingMore?.collectAsState()
        val appointmentList = appointmentResourceListEnvelopeViewModel?.resources?.collectAsState()
        val totalAppointmentsCount =
            appointmentResourceListEnvelopeViewModel?.totalItemCount?.collectAsState()
        val displayedAppointmentsCount =
            appointmentResourceListEnvelopeViewModel?.displayedItemCount?.collectAsState()

        val uiState = loadingScreenUiStateViewModel!!.uiStateInfo.collectAsState()
        val deleteActionUIStates = deletePerformedActionUIStateViewModel!!.deleteUIStateInfo.collectAsState()
        val postponeActionUIStates = postponementViewModel!!.postponementViewUIState.collectAsState()
        val joinMeetingActionUIStates = joinMeetingPerformedActionUIStateViewModel!!.joinMeetingStateInfo.collectAsState()
        val refreshActionUIStates = refreshActionUIStateViewModel!!.refreshAppointmentActionUiState.collectAsState()
        val isRefreshing = remember { mutableStateOf(false) }
        val userId = preferenceSettings[SharedPreferenceEnum.PROFILE_ID.toPath(),-1L]

        val lastIndex = appointmentList?.value?.size?.minus(1)
        val selectedAppointment = remember { mutableStateOf(UserAppointment()) }

        LaunchedEffect(true) {
            if (appointmentResourceListEnvelopeViewModel!!.resources.value.isNotEmpty()){
                appointmentPresenter.refreshUserAppointments(userId)
                loadingScreenUiStateViewModel!!.switchScreenUIState(AppUIStates(isSuccess = true))
            }
            else {
                appointmentResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                appointmentPresenter.getUserAppointments(userId)
            }

        }

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
                    appointmentList = appointmentResourceListEnvelopeViewModel!!.resources.value)
        }

        if (postponeActionUIStates.value.isLoading) {
            Box(modifier = Modifier.fillMaxWidth()) {
                LoadingDialog("Updating Appointment")
            }
        }
        else if (postponeActionUIStates.value.isSuccess) {
            Box(modifier = Modifier.fillMaxWidth()) {
                SuccessDialog("Appointment Postponed", "Close", onConfirmation = {
                    appointmentPresenter.refreshUserAppointments(userId)
                    postponementViewModel!!.setPostponementViewUIState(AppUIStates(isDefault = true))
                })
            }
        }

        else if (postponeActionUIStates.value.isFailed) {
            Box(modifier = Modifier.fillMaxWidth()) {
                ErrorDialog("Error Postponing Appointment", "Close", onConfirmation = {})
            }
        }
        if (deleteActionUIStates.value.isLoading) {
            Box(modifier = Modifier.fillMaxWidth()) {
                LoadingDialog("Deleting Appointment")
            }
        }
        else if (deleteActionUIStates.value.isSuccess) {
            Box(modifier = Modifier.fillMaxWidth()) {
                SuccessDialog("Delete Successful", "Close", onConfirmation = {
                    appointmentResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                    appointmentPresenter.getUserAppointments(userId!!)
                    deletePerformedActionUIStateViewModel!!.switchActionDeleteUIState(AppUIStates(isDefault = true))
                })
            }
        }
        else if (deleteActionUIStates.value.isFailed) {
            ErrorDialog("Error Deleting Appointment", "Close", onConfirmation = {})
        }

        if (refreshActionUIStates.value.isLoading){
            isRefreshing.value = true
        }
        else if(refreshActionUIStates.value.isSuccess){
            isRefreshing.value = false
        }
        else if (refreshActionUIStates.value.isFailed){
            isRefreshing.value = false
        }





        if (joinMeetingActionUIStates.value.isLoading) {
            Box(modifier = Modifier.fillMaxWidth()) {
                LoadingDialog("Joining Meeting")
            }
        }
        else if (joinMeetingActionUIStates.value.isSuccess) {}
        else if (joinMeetingActionUIStates.value.isFailed) {
            Box(modifier = Modifier.fillMaxWidth()) {
                ErrorDialog("Error Joining Meeting Please Try Again", actionTitle = "Retry", onConfirmation = {
                    
                })
            }
        }


        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {},
            content = {
                val handler = AppointmentsHandler(
                    appointmentResourceListEnvelopeViewModel!!,
                    refreshActionUIStateViewModel!!,
                    loadingScreenUiStateViewModel!!,
                    deletePerformedActionUIStateViewModel!!,
                    joinMeetingPerformedActionUIStateViewModel!!,
                    postponeTimeUIStateViewModel!!,
                    postponementViewModel!!,
                    appointmentPresenter,
                    onMeetingTokenReady = {}
                )
                handler.init()

                if (uiState.value.isLoading) {
                    //Content Loading
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                } else if (uiState.value.isFailed) {

                    //Error Occurred display reload

                } /*else if (uiState.value.emptyContent) {

                    //Error Occurred display reload

                }*/
                else if (uiState.value.isSuccess) {
                val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing.value, onRefresh = {
                    appointmentPresenter.refreshUserAppointments(userId)
                })
                PullRefreshLayout(
                        modifier = Modifier.fillMaxSize(),
                        state = pullRefreshState,
                        flipped = false,
                        indicator = {
                            PullRefreshIndicator(
                                state = pullRefreshState,
                                flipped = false,
                                backgroundColor = Colors.primaryColor,
                                contentColor = Colors.darkPrimary
                            )
                        }
                    ) {
                       LazyColumn(
                                modifier = Modifier.fillMaxWidth()
                                    .height(getAppointmentViewHeight(appointmentUIModel.appointmentList.size).dp)
                                    .padding(bottom = 70.dp),
                                userScrollEnabled = true
                            ) {
                           itemsIndexed(items = appointmentUIModel.appointmentList) { it, item ->
                               if (item.resources?.appointmentType == AppointmentType.MEETING.toPath()) {
                                   MeetingAppointmentWidget(
                                       userAppointment = item,
                                       appointmentPresenter = appointmentPresenter,
                                       isFromHomeTab = false
                                   )
                               } else {
                                   AppointmentWidget(
                                       item,
                                       appointmentPresenter = appointmentPresenter,
                                       postponementViewModel = postponementViewModel,
                                       mainViewModel = mainViewModel!!,
                                       postponeTimeUIStateViewModel!!,
                                       isFromHomeTab = false,
                                       onDeleteAppointment = {
                                           appointmentPresenter.deleteAppointment(it.appointmentId!!)
                                       },
                                       platformNavigator = platformNavigator
                                   )
                               }
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
                                           if (userId != -1L) {
                                               appointmentPresenter.getMoreAppointments(
                                                   userId!!,
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
            })
    }
}
