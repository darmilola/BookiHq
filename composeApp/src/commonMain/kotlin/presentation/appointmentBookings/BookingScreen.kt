package presentation.appointmentBookings

import StackedSnackbarHost
import StackedSnakbarHostState
import UIStates.AppUIStates
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
import androidx.room.RoomDatabase
import applications.date.getDay
import applications.date.getMonth
import applications.date.getYear
import applications.room.AppDatabase
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
import domain.Models.PaymentAuthorizationResult
import domain.Models.PaymentCard
import domain.Models.PlatformNavigator
import domain.Models.VendorPackage
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import presentation.Screens.AddDebitCardScreen
import presentation.dialogs.AddDebitCardDialog
import presentation.payment.PaymentContract
import presentation.payment.PaymentPresenter
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.widgets.AppointmentPaymentMethodBottomSheet
import presentation.widgets.PaymentCardBottomSheet
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import rememberStackedSnackbarHostState
import utils.ParcelableScreen
import utils.calculateAppointmentPaymentAmount
import utils.calculatePackageAppointmentPaymentAmount


@OptIn(ExperimentalVoyagerApi::class)
@Parcelize
class BookingScreen(val platformNavigator: PlatformNavigator) :  KoinComponent, ScreenTransition, ParcelableScreen {
    @Transient private val bookingPresenter: BookingPresenter by inject()
    @Transient private var loadPendingActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var deleteActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var createAppointmentActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var paymentActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var getTherapistActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var bookingViewModel: BookingViewModel? = null
    @Transient private val paymentPresenter: PaymentPresenter by inject()
    @Transient private var mainViewModel: MainViewModel? = null
    @Transient
    private var getTimeActionUIStateViewModel: PerformedActionUIStateViewModel? = null
    @Transient private var databaseBuilder: RoomDatabase.Builder<AppDatabase>? = null
    @Transient var cardList = listOf<PaymentCard>()
    private var selectedCard: PaymentCard? = null
    private var vendorPackage: VendorPackage? = null

   override val key: ScreenKey = uniqueScreenKey

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    fun setDatabaseBuilder(databaseBuilder: RoomDatabase.Builder<AppDatabase>?){
        this.databaseBuilder = databaseBuilder
    }

    fun setSelectedPackage(vendorPackage: VendorPackage){
        this.vendorPackage = vendorPackage
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val pagerState = rememberPagerState(pageCount = { 3 })
        val addMoreService = remember { mutableStateOf(false) }
        val lastItemRemoved = remember { mutableStateOf(false) }
        val currentUserInfo = mainViewModel!!.currentUserInfo.value
        val customerEmail = if (currentUserInfo.email!!.isNotEmpty()) currentUserInfo.email else "damilolaakinterinwa@gmail.com"
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()
        val currentPage = remember { mutableStateOf(-1) }


        if (loadPendingActionUIStateViewModel == null) {
            loadPendingActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (deleteActionUIStateViewModel == null) {
            deleteActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (createAppointmentActionUIStateViewModel == null) {
            createAppointmentActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (getTherapistActionUIStateViewModel == null) {
            getTherapistActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (getTimeActionUIStateViewModel == null) {
            getTimeActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    PerformedActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        if (paymentActionUIStateViewModel== null) {
            paymentActionUIStateViewModel = kmpViewModel(
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
            when (currentPage.value) {
                0 -> {
                    mainViewModel!!.setOnBackPressed(false)
                    navigator.pop()
                }
                1 -> {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }
                2 -> {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                        val lastBooking = bookingViewModel!!.pendingAppointments.value[0]
                        bookingPresenter.silentDeletePendingBookingAppointment(lastBooking.appointmentId)
                    }

                }
            }

        }

        val openAddDebitCardDialog = remember { mutableStateOf(false) }

        when {
            openAddDebitCardDialog.value -> {
                AddDebitCardDialog(databaseBuilder, onDismissRequest = {
                    openAddDebitCardDialog.value = false
                }, onConfirmation = {
                    openAddDebitCardDialog.value = false
                })
            }
        }

        val handler = BookingScreenHandler(
            bookingViewModel!!, loadPendingActionUIStateViewModel = loadPendingActionUIStateViewModel!!, deleteActionUIStateViewModel = deleteActionUIStateViewModel!!,
            createAppointmentActionUIStateViewModel = createAppointmentActionUIStateViewModel!!, getTherapistActionUIStateViewModel = getTherapistActionUIStateViewModel!!, getTimesActionUIStateViewModel = getTimeActionUIStateViewModel!!,bookingPresenter, onShowUnsavedAppointment = {})
        handler.init()


        val createAppointmentActionUiStates = createAppointmentActionUIStateViewModel!!.createAppointmentUiState.collectAsState()
        val paymentActionUiState = paymentActionUIStateViewModel!!.paymentUiStateInfo.collectAsState()

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

        if (paymentActionUiState.value.isLoading) {
            Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                LoadingDialog("Processing Payment")
            }
        }
        else if (paymentActionUiState.value.isFailed) {
            Box(modifier = Modifier.fillMaxWidth()) {
                ErrorDialog("Payment Authentication Failed", actionTitle = "Retry", onConfirmation = {})
            }
        }


        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        if (addMoreService.value){
            addMoreService.value = false
            rememberCoroutineScope().launch {
                pagerState.scrollToPage(0)
                navigator.pop()
            }
        }
        if (lastItemRemoved.value){
            lastItemRemoved.value = false
            rememberCoroutineScope().launch {
                pagerState.scrollToPage(0)
                navigator.pop()
            }
        }

        val showPaymentMethodBottomSheet = mainViewModel!!.showPaymentMethodBottomSheet.collectAsState()

        if (showPaymentMethodBottomSheet.value) {
            AppointmentPaymentMethodBottomSheet(
                mainViewModel!!,
                onDismiss = {
                    mainViewModel!!.showPaymentMethodBottomSheet(false)
                },
                onCardPaymentSelected = {
                    runBlocking {
                        cardList = databaseBuilder!!.build().getPaymentCardDao().getAllPaymentCards()
                    }
                    mainViewModel!!.showPaymentMethodBottomSheet(false)
                    mainViewModel!!.showPaymentCardsBottomSheet(true)

                }, onCashSelected = {
                    val userId = mainViewModel!!.currentUserInfo.value.userId
                    val vendorId = mainViewModel!!.connectedVendor.value.vendorId
                    val paymentAmount = calculateAppointmentPaymentAmount(bookingViewModel!!.pendingAppointments.value)
                    bookingPresenter.createAppointment(userId!!, vendorId!!, bookingStatus = BookingStatus.DONE.toPath(), day = getDay(),
                        month = getMonth(), year = getYear(), paymentAmount = paymentAmount,
                        paymentMethod = PaymentMethod.PAYMENT_ON_DELIVERY.toPath())
                    mainViewModel!!.showPaymentMethodBottomSheet(false)
                })
        }

        val paymentHandler = CreateAppointmentPaymentHandler(
            paymentPresenter = paymentPresenter,
            paymentActionUIStateViewModel!!,
            onAuthorizationSuccessful = {
                val userId = mainViewModel!!.currentUserInfo.value.userId
                val vendorId = mainViewModel!!.connectedVendor.value.vendorId
                if (it.status) {
                    val paymentAmount = calculateAppointmentPaymentAmount(bookingViewModel!!.pendingAppointments.value)
                    platformNavigator.startPaymentProcess(paymentAmount = paymentAmount.toString(),
                        customerEmail = customerEmail,
                        accessCode = it.paymentAuthorizationData.accessCode,
                        paymentCard = selectedCard!!,
                        onPaymentLoading = {},
                        onPaymentSuccessful = {
                            bookingPresenter.createAppointment(userId!!, vendorId!!, bookingStatus = BookingStatus.DONE.toPath(), day = getDay(),
                                month = getMonth(), year = getYear(), paymentAmount = paymentAmount,
                                paymentMethod = PaymentMethod.CARD_PAYMENT.toPath())
                        },
                        onPaymentFailed = {})
                }
            })
        paymentHandler.init()

        val showSelectPaymentCards = mainViewModel!!.showPaymentCardsBottomSheet.collectAsState()

        if (showSelectPaymentCards.value) {
            val paymentAmount = calculateAppointmentPaymentAmount(bookingViewModel!!.pendingAppointments.value)
            mainViewModel!!.showPaymentMethodBottomSheet(false)
            PaymentCardBottomSheet(
                mainViewModel!!,
                cardList,
                onCardSelected = {
                    mainViewModel!!.showPaymentCardsBottomSheet(false)
                    selectedCard = it
                    paymentPresenter.initCheckOut(amount = paymentAmount.toString(), customerEmail = customerEmail)
                },
                onDismiss = {
                    mainViewModel!!.showPaymentCardsBottomSheet(false)
                }, onAddNewSelected = {
                    mainViewModel!!.showPaymentCardsBottomSheet(false)
                    openAddDebitCardDialog.value = true
                })
        }

        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState)  },
            topBar = {},
            content = {
                val layoutModifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = Color.White)
                Column(modifier = layoutModifier) {

                    BookingScreenTopBar(pagerState, onBackPressed = { page ->
                        currentPage.value = page
                        when (currentPage.value) {
                            0 -> {
                                navigator.pop()
                            }
                            1 -> {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(0)
                                }
                            }
                            2 -> {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(1)
                                    val lastBooking = bookingViewModel!!.pendingAppointments.value[0]
                                    bookingPresenter.silentDeletePendingBookingAppointment(lastBooking.appointmentId)
                                }

                            }
                        }
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
                                getTherapistActionUIStateViewModel!!,
                                loadPendingActionUIStateViewModel!!,
                                deleteActionUIStateViewModel!!,
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
        )}

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
                        .border(
                            border = BorderStroke(1.dp, color = Colors.primaryColor),
                            shape = CircleShape
                        )
                        .height(45.dp),
                    buttonText = "Add More Bookings",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    fontSize = 16,
                    shape = RoundedCornerShape(10.dp),
                    textColor = Colors.primaryColor,
                    style = MaterialTheme.typography.h6,
                    borderStroke = null
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
                            if (bookingViewModel?.currentAppointmentBooking?.value?.serviceTypeId == -1L) {
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
                    mainViewModel.showPaymentMethodBottomSheet(true)
                  }
                }
            }
        }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun AttachBookingPages(pagerState: PagerState, getTherapistActionUIStateViewModel: PerformedActionUIStateViewModel,
                           loadPendingActionUIStateViewModel: PerformedActionUIStateViewModel, deleteActionUIStateViewModel: PerformedActionUIStateViewModel,
                           mainViewModel: MainViewModel, bookingViewModel: BookingViewModel, services: Services, onLastItemRemoved:() -> Unit){
        val pageHeight = remember { mutableStateOf(0.90f) }
        val boxModifier =
            Modifier
                .padding(top = 5.dp)
                .background(color = Color.White)
                .fillMaxHeight(pageHeight.value)
                .fillMaxWidth()

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
                         BookingSelectTherapists(mainViewModel,getTherapistActionUIStateViewModel,bookingViewModel,bookingPresenter)
                    }
                    2 -> if(page == pagerState.targetPage) {
                        pageHeight.value = 0.80f
                        BookingOverview(
                            mainViewModel,
                            bookingPresenter,
                            bookingViewModel,
                            loadPendingActionUIStateViewModel = loadPendingActionUIStateViewModel,
                            deleteActionUIStateViewModel = deleteActionUIStateViewModel,
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

class CreateAppointmentPaymentHandler(
    private val paymentPresenter: PaymentPresenter,
    private val paymentUiStateViewModel: PerformedActionUIStateViewModel,
    private val onAuthorizationSuccessful: (PaymentAuthorizationResult) -> Unit
) : PaymentContract.View {
    fun init() {
        paymentPresenter.registerUIContract(this)
    }
    override fun showPaymentLce(appUIStates: AppUIStates) {
        paymentUiStateViewModel.switchPaymentActionUIState(appUIStates)
    }

    override fun showAuthorizationResult(paymentAuthorizationResult: PaymentAuthorizationResult) {
        onAuthorizationSuccessful(paymentAuthorizationResult)
    }
}


