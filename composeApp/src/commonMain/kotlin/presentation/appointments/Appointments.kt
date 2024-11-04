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
import domain.Enums.AppointmentType
import domain.Enums.SharedPreferenceEnum
import domain.Models.Appointment
import drawable.ErrorOccurredWidget
import kotlinx.serialization.Transient
import presentation.DomainViewHandler.CancelContractHandler
import presentation.appointmentBookings.BookingPresenter
import presentation.dialogs.ErrorDialog
import presentation.dialogs.SuccessDialog
import presentation.widgets.AddAppointmentsReviewBottomSheet
import presentation.widgets.AppointmentWidget
import presentation.widgets.EmptyContentWidget
import presentation.widgets.PackageAppointmentWidget
import presentation.widgets.PendingPackageAppointmentWidget
import utils.getAppointmentViewHeight
import rememberStackedSnackbarHostState
import theme.Colors

@Parcelize
class AppointmentsTab(private val platformNavigator: PlatformNavigator) : Tab, KoinComponent, Parcelable {

    @Transient private val appointmentPresenter: AppointmentPresenter by inject()
    @Transient private val bookingPresenter: BookingPresenter by inject()
    @Transient private var loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel? = null
    @Transient private var deletePerformedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var postponePerformedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var refreshActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var getTherapistAvailabilityUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var addAppointmentReviewsUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var postponeAppointmentUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var joinMeetingPerformedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var cancelActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var postponementViewModel: PostponementViewModel? = null
    @Transient private var mainViewModel: MainViewModel? = null
    @Transient val preferenceSettings = Settings()
    @Transient private var appointmentResourceListEnvelopeViewModel: AppointmentResourceListEnvelopeViewModel? = null


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

        if (postponeAppointmentUIStateViewModel == null) {
            postponeAppointmentUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (cancelActionUIStateViewModel == null) {
            cancelActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
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

        if (getTherapistAvailabilityUIStateViewModel == null) {
            getTherapistAvailabilityUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (addAppointmentReviewsUIStateViewModel == null) {
            addAppointmentReviewsUIStateViewModel = kmpViewModel(
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
        val cancelActionUIStates = cancelActionUIStateViewModel!!.deletePendingAppointmentUiState.collectAsState()
        val refreshActionUIStates = refreshActionUIStateViewModel!!.refreshAppointmentActionUiState.collectAsState()
        val addReviewsUIState = addAppointmentReviewsUIStateViewModel!!.addAppointmentReviewUiState.collectAsState()
        val postponeActionUIStates = postponeAppointmentUIStateViewModel!!.postponeAppointmentUiState.collectAsState()
        val isRefreshing = remember { mutableStateOf(false) }
        val userId = preferenceSettings[SharedPreferenceEnum.USER_ID.toPath(),-1L]

        val lastIndex = appointmentList?.value?.size?.minus(1)
        val selectedAppointment = remember { mutableStateOf(UserAppointment()) }
        val appointmentForReview = remember { mutableStateOf(Appointment()) }

        val isSwitchVendor: Boolean = preferenceSettings[SharedPreferenceEnum.IS_SWITCH_VENDOR.toPath(),false]
        if (isSwitchVendor){
            appointmentResourceListEnvelopeViewModel!!.setResources(arrayListOf())
        }

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
                    postponeAppointmentUIStateViewModel!!.switchPostPostponeAppointmentUiState(AppUIStates(isDefault = true))
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
        else if (deleteActionUIStates.value.isFailed) {
            Box(modifier = Modifier.fillMaxWidth()) {
                ErrorDialog("Error Occurred Please Try Again", "Close", onConfirmation = {})
            }
        }
        else if (deleteActionUIStates.value.isSuccess) {
            Box(modifier = Modifier.fillMaxWidth()) {
                SuccessDialog("Delete Successful", "Close", onConfirmation = {
                    appointmentResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                    appointmentPresenter.getUserAppointments(userId)
                    deletePerformedActionUIStateViewModel!!.switchActionDeleteUIState(AppUIStates(isDefault = true))
                })
            }
        }



        if (cancelActionUIStates.value.isLoading) {
            Box(modifier = Modifier.fillMaxWidth()) {
                LoadingDialog("Cancelling Appointment")
            }
        }
        else if (cancelActionUIStates.value.isFailed) {
            Box(modifier = Modifier.fillMaxWidth()) {
                ErrorDialog("Error Occurred Please Try Again", "Close", onConfirmation = {})
            }
        }
        else if (cancelActionUIStates.value.isSuccess) {
            Box(modifier = Modifier.fillMaxWidth()) {
                SuccessDialog("Cancel Successful", "Close", onConfirmation = {
                    appointmentResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                    appointmentPresenter.getUserAppointments(userId)
                    cancelActionUIStateViewModel!!.switchDeletePendingAppointmentUiState(AppUIStates(isDefault = true))
                })
            }
        }



        if (addReviewsUIState.value.isLoading) {
            Box(modifier = Modifier.fillMaxWidth()) {
                LoadingDialog("Adding Review")
            }
        }
        else if (addReviewsUIState.value.isSuccess) {
            Box(modifier = Modifier.fillMaxWidth()) {
                SuccessDialog("Review Added Successfully", "Close", onConfirmation = {
                    addAppointmentReviewsUIStateViewModel!!.switchActionDeleteUIState(AppUIStates(isDefault = true))
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

        val cancelContractHandler = CancelContractHandler(cancelActionUIStateViewModel!!, bookingPresenter)
        cancelContractHandler.init()


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
                    addAppointmentReviewsUIStateViewModel!!,
                    getTherapistAvailabilityUIStateViewModel!!,
                    postponeAppointmentUIStateViewModel!!,
                    postponementViewModel!!,
                    appointmentPresenter,
                    onMeetingTokenReady = {}
                )
                handler.init()

                val showAddReviewBottomSheet = mainViewModel!!.showAppointmentReviewsBottomSheet.collectAsState()

                if (showAddReviewBottomSheet.value) {
                    AddAppointmentsReviewBottomSheet(
                        mainViewModel!!,
                        onDismiss = {
                            mainViewModel!!.showAppointmentReviewsBottomSheet(false)
                        },
                        onReviewsAdded = {
                            appointmentPresenter.addAppointmentReviews(userId = userId, appointmentId = appointmentForReview.value.appointmentId!!,
                                vendorId = appointmentForReview.value.vendorId, serviceTypeId = appointmentForReview.value.serviceTypeId!!, therapistId = appointmentForReview.value.therapistId,reviewText = it)
                            mainViewModel!!.showAppointmentReviewsBottomSheet(false)
                        })
                   }

                if (uiState.value.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                } else if (uiState.value.isFailed) {
                   Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                       ErrorOccurredWidget(uiState.value.errorMessage, onRetryClicked = {
                           appointmentResourceListEnvelopeViewModel!!.clearData(mutableListOf())
                           appointmentPresenter.getUserAppointments(userId)
                       })
                   }
                }
                else if (uiState.value.isEmpty) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        EmptyContentWidget(emptyText = uiState.value.emptyMessage)
                    }
                }
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
                               if (item.resources!!.appointmentType == AppointmentType.SINGLE.toPath()) {
                                   AppointmentWidget(
                                       item,
                                       appointmentPresenter = appointmentPresenter,
                                       postponementViewModel = postponementViewModel,
                                       mainViewModel = mainViewModel!!,
                                       getTherapistAvailabilityUIStateViewModel!!,
                                       onDeleteAppointment = {
                                           appointmentPresenter.deleteAppointment(it.id)
                                       },
                                       onCancelAppointment = {
                                           bookingPresenter.deletePendingBookingAppointment(it.resources!!.appointmentId!!)
                                       },
                                       platformNavigator = platformNavigator,
                                       onAddReview = {
                                           appointmentForReview.value = it
                                           mainViewModel!!.showAppointmentReviewsBottomSheet(true)
                                       })
                               }
                               else if (item.resources.appointmentType == AppointmentType.PACKAGE.toPath()) {
                                   PackageAppointmentWidget(
                                       item,
                                       appointmentPresenter = appointmentPresenter,
                                       postponementViewModel = postponementViewModel,
                                       mainViewModel = mainViewModel!!,
                                       getTherapistAvailabilityUIStateViewModel!!,
                                       onDeleteAppointment = {
                                           appointmentPresenter.deleteAppointment(it.id)
                                       },
                                       onCancelAppointment = {
                                          bookingPresenter.deletePendingBookingAppointment(it.resources!!.appointmentId!!)
                                       },
                                       platformNavigator = platformNavigator,
                                       onAddReview = {
                                           appointmentForReview.value = it
                                           mainViewModel!!.showAppointmentReviewsBottomSheet(true)
                                       })
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
                                                   userId,
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
