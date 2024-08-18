package presentation.bookings

import StackedSnackbarHost
import StackedSnakbarHostState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import theme.styles.Colors
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Enums.Screens
import domain.Models.Services
import presentation.components.ButtonComponent
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.BookingScreenHandler
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.dialogs.SuccessDialog
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.IntOffset
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.BookingStatus
import domain.Enums.PaymentMethod
import domain.Models.PlatformNavigator
import kotlinx.serialization.Transient
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import rememberStackedSnackbarHostState
import utils.ParcelableScreen


@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class BookingScreen(val platformNavigator: PlatformNavigator) :  KoinComponent, ScreenTransition, ParcelableScreen {
    @Transient private val bookingPresenter: BookingPresenter by inject()
    @Transient private var performedActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var bookingViewModel: BookingViewModel? = null
    @Transient private var mainViewModel: MainViewModel? = null

   override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val pagerState = rememberPagerState(pageCount = { 3 })
        val addMoreService = remember { mutableStateOf(false) }
        val lastItemRemoved = remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()


        if (performedActionUIStateViewModel == null) {
            performedActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

      if(bookingViewModel == null) {
        bookingViewModel = kmpViewModel(
            factory = viewModelFactory {
                BookingViewModel(savedStateHandle = createSavedStateHandle())
            },
        )
     }

        val onBackPressed = mainViewModel!!.onBackPressed.collectAsState()
        if (onBackPressed.value){
            mainViewModel!!.setOnBackPressed(false)
            navigator.pop()
        }


        val handler = BookingScreenHandler(
            bookingViewModel!!, performedActionUIStateViewModel = performedActionUIStateViewModel!! ,bookingPresenter, onShowUnsavedAppointment = {})
        handler.init()


        val createAppointmentActionUiStates = performedActionUIStateViewModel!!.createAppointmentUiState.collectAsState()

        if (createAppointmentActionUiStates.value.isLoading) {
            Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                LoadingDialog("Creating Appointment...")
            }
        }
        else if (createAppointmentActionUiStates.value.isSuccess){
            Box(modifier = Modifier.fillMaxWidth()) {
                SuccessDialog("Creating Appointment Successful", actionTitle = "Done", onConfirmation = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(0)
                        navigator.pop()
                    }
                })
            }
        }
        else if (createAppointmentActionUiStates.value.isFailed){
            Box(modifier = Modifier.fillMaxWidth()) {
                ErrorDialog("Creating Appointment Failed", actionTitle = "Retry", onConfirmation = {})
            }
        }


        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        if (addMoreService.value){
            rememberCoroutineScope().launch {
                pagerState.scrollToPage(0)
                navigator.pop()
            }
        }
        if (lastItemRemoved.value){
            rememberCoroutineScope().launch {
                pagerState.scrollToPage(0)
                navigator.pop()
            }
        }
        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState)  }
        ) {

            val layoutModifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.White)
            Column(modifier = layoutModifier) {

                BookingScreenTopBar(pagerState, onBackPressed = {
                    navigator.pop()
                })

                val bgStyle = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .fillMaxHeight()

                Box(
                    contentAlignment = Alignment.TopCenter, modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color.White)
                ) {

                    Column(
                        modifier = bgStyle
                    ) {
                        AttachBookingPages(
                            pagerState,
                            performedActionUIStateViewModel!!,
                            mainViewModel!!,
                            bookingViewModel!!,
                            services = mainViewModel!!.selectedService.value,
                            onLastItemRemoved = {
                                lastItemRemoved.value = true
                            }
                        )
                        AttachActionButtons(pagerState, mainViewModel!!, stackedSnackBarHostState, bookingPresenter, onAddMoreServicesClicked = {
                            addMoreService.value = true
                        })
                    }
                }

            }
        }

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun AttachActionButtons(pagerState: PagerState, mainViewModel: MainViewModel, stackedSnackBarHostState: StackedSnakbarHostState, bookingPresenter: BookingPresenter, onAddMoreServicesClicked: () -> Unit){

        var btnFraction by remember { mutableStateOf(0f) }
        val currentPage = pagerState.currentPage


        btnFraction = if (pagerState.currentPage == 1){
            0.5f
        } else {
            0f
        }

        val coroutineScope = rememberCoroutineScope()
        Column(modifier = Modifier
            .padding(bottom = 10.dp,start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center) {

            if (currentPage == 2) {
                    ButtonComponent(
                        modifier = Modifier
                            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth()
                            .background(color = Color.Transparent, shape = CircleShape)
                            .border(border = BorderStroke(1.dp, color = Colors.primaryColor), shape = CircleShape)
                            .height(45.dp),
                        buttonText = "Add More Bookings",
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        fontSize = 16, shape = RoundedCornerShape(10.dp),
                        textColor = Colors.primaryColor, style = MaterialTheme.typography.h6, borderStroke = null
                    ) {
                        onAddMoreServicesClicked()
                    }
              }

            val bookingNavText = if(currentPage == 2) "Go To Payments" else "Continue"
            ButtonComponent(modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)
                .fillMaxWidth()
                .clip(CircleShape)
                .height(45.dp),
                buttonText = bookingNavText,
                colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                fontSize = 16, shape = RoundedCornerShape(10.dp),
                textColor = Color(color = 0xFFFFFFFF), style = MaterialTheme.typography.h6, borderStroke = null) {

                coroutineScope.launch {
                        if (currentPage == 0) {
                            if (bookingViewModel?.currentAppointmentBooking?.value?.serviceTypeId == -1) {
                                ShowSnackBar(title = "No Service Selected",
                                    description = "Please Select a Service to proceed",
                                    actionLabel = "",
                                    duration = StackedSnackbarDuration.Short,
                                    snackBarType = SnackBarType.ERROR,
                                    stackedSnackBarHostState,
                                    onActionClick = {})
                            } else {
                                pagerState.animateScrollToPage(1)
                            }
                        } else if (currentPage == 1) {
                            if (bookingViewModel?.currentAppointmentBooking?.value?.serviceTypeTherapists == null) {
                                ShowSnackBar(title = "No Therapist Selected",
                                    description = "Please Select a Therapist to proceed",
                                    actionLabel = "",
                                    duration = StackedSnackbarDuration.Short,
                                    snackBarType = SnackBarType.ERROR,
                                    stackedSnackBarHostState,
                                    onActionClick = {})
                            } else if (bookingViewModel?.currentAppointmentBooking?.value?.pendingTime == null) {
                                ShowSnackBar(title = "No Time Selected",
                                    description = "Please Select Appointment Time to proceed",
                                    actionLabel = "",
                                    duration = StackedSnackbarDuration.Short,
                                    snackBarType = SnackBarType.ERROR,
                                    stackedSnackBarHostState,
                                    onActionClick = {})
                            } else {
                                pagerState.animateScrollToPage(2)
                            }
                        }
                    }
                if (currentPage == 2){
                    val userId = mainViewModel.currentUserInfo.value.userId
                    val vendorId = mainViewModel.connectedVendor.value.vendorId
                    bookingPresenter.createAppointment(userId!!, vendorId!!, bookingStatus = BookingStatus.DONE.toPath(), day = bookingViewModel!!.day.value!!, month = bookingViewModel!!.month.value, year = bookingViewModel!!.year.value, paymentAmount = 4500.0, paymentMethod = PaymentMethod.CARD_PAYMENT.toPath())
                }
                }
            }
        }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun AttachBookingPages(pagerState: PagerState, performedActionUIStateViewModel: PerformedActionUIStateViewModel, mainViewModel: MainViewModel, bookingViewModel: BookingViewModel, services: Services, onLastItemRemoved:() -> Unit){
        val pageHeight = remember { mutableStateOf(0.90f) }
        val boxModifier =
            Modifier
                .padding(top = 5.dp)
                .background(color = Color.White)
                .fillMaxHeight(pageHeight.value)
                .fillMaxWidth()

        // AnimationEffect
        Box(contentAlignment = Alignment.TopCenter, modifier = boxModifier) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> if (page == pagerState.targetPage){
                        pageHeight.value = 0.90f
                        BookingSelectServices(mainViewModel, bookingViewModel,services)
                    }
                    1 -> if(page == pagerState.targetPage) {
                         pageHeight.value = 0.90f
                         BookingSelectTherapists(mainViewModel,performedActionUIStateViewModel,bookingViewModel,bookingPresenter)
                    }
                    2 -> if(page == pagerState.targetPage) {
                        pageHeight.value = 0.80f
                        BookingCheckOut(
                            mainViewModel,
                            bookingPresenter,
                            bookingViewModel,
                            performedActionUIStateViewModel,
                            onLastItemRemoved = {
                                onLastItemRemoved()
                           })
                    }
                }
            }

        }

    }

    override fun enter(lastEvent: StackEvent): EnterTransition {
        return slideIn { size ->
            val x = if (lastEvent == StackEvent.Pop) -size.width else size.width
            IntOffset(x = x, y = 0)
        }
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        return slideOut { size ->
            val x = if (lastEvent == StackEvent.Pop) size.width else -size.width
            IntOffset(x = x, y = 0)
        }
    }


}

