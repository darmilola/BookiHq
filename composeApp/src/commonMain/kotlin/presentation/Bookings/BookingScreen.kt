package presentation.Bookings

import StackedSnackbarHost
import StackedSnakbarHostState
import theme.styles.Colors
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
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
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import domain.Models.AuthSSOScreenNav
import domain.Models.Screens
import domain.Models.ServiceTypeSpecialist
import domain.Models.Services
import domain.Models.User
import presentation.components.ButtonComponent
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.authentication.AuthenticationContract
import presentation.authentication.AuthenticationPresenter
import presentation.authentication.AuthenticationScreenHandler
import presentation.viewmodels.AuthenticationViewModel
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.UIStates
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import rememberStackedSnackbarHostState


class BookingScreen(private val mainViewModel: MainViewModel) : Tab, KoinComponent {
    private val bookingPresenter: BookingPresenter by inject()
    private var uiStateViewModel: UIStateViewModel? = null
    private var bookingViewModel: BookingViewModel? = null
    override val options: TabOptions
        @Composable
        get() {
            val title = "Bookings"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {

        if (uiStateViewModel == null) {
            uiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
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



        // View Contract Handler Initialisation
        val handler = BookingScreenHandler(
            bookingViewModel!!, bookingPresenter,
          onPageLoading = {
               uiStateViewModel!!.switchState(UIStates(loadingVisible = true))
        }, onContentVisible = {
                uiStateViewModel!!.switchState(UIStates(contentVisible = true))
            }, onErrorVisible = {
                uiStateViewModel!!.switchState(UIStates(errorOccurred = true))
            })
        handler.init()




        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        val pagerState = rememberPagerState(pageCount = {
            3
        })
        val addMoreService = remember { mutableStateOf(false) }

        if (addMoreService.value){
            rememberCoroutineScope().launch {
                pagerState.scrollToPage(0)
                bookingViewModel!!.setCurrentBookingId(-1)
                mainViewModel.setScreenNav(Pair(Screens.BOOKING.toPath(), Screens.MAIN_TAB.toPath()))
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

                BookingScreenTopBar(pagerState, mainViewModel, bookingViewModel!!)

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
                            services = mainViewModel.selectedService.value,
                            onAddMoreServiceClicked = {
                                addMoreService.value = true
                            }
                        )
                        AttachActionButtons(pagerState, mainViewModel, stackedSnackBarHostState)
                    }
                }

            }
        }

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun AttachActionButtons(pagerState: PagerState, mainViewModel: MainViewModel, stackedSnackBarHostState: StackedSnakbarHostState){

        var btnFraction by remember { mutableStateOf(0f) }
        val currentPage = pagerState.currentPage


        btnFraction = if (pagerState.currentPage == 1){
            0.5f
        } else {
            0f
        }


        val coroutineScope = rememberCoroutineScope()

        val buttonStyle = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .fillMaxWidth()
            .clip(CircleShape)
            .height(45.dp)


        Row (modifier = Modifier
            .padding(bottom = 10.dp,start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,) {

            val bookingNavText = if(currentPage == 2) "Go To Payments" else "Continue"

            ButtonComponent(modifier = buttonStyle, buttonText = bookingNavText,
                colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                fontSize = 16, shape = RoundedCornerShape(10.dp),
                textColor = Color(color = 0xFFFFFFFF), style = MaterialTheme.typography.h6, borderStroke = null) {
                coroutineScope.launch {
                    if(currentPage == 0){
                        if(bookingViewModel?.selectedServiceType?.value?.serviceId == -1) {
                            ShowSnackBar(title = "No Service Selected",
                                description = "Please Select a Service to proceed",
                                actionLabel = "",
                                duration = StackedSnackbarDuration.Short,
                                snackBarType = SnackBarType.ERROR,
                                stackedSnackBarHostState,
                                onActionClick = {})
                        }
                        else{
                            pagerState.animateScrollToPage(1)
                         }
                    }
                    else if(currentPage == 1){
                        if(bookingViewModel?.currentAppointmentBooking?.value?.serviceTypeSpecialist == null) {
                            ShowSnackBar(title = "No Therapist Selected",
                                description = "Please Select a Therapist to proceed",
                                actionLabel = "",
                                duration = StackedSnackbarDuration.Short,
                                snackBarType = SnackBarType.ERROR,
                                stackedSnackBarHostState,
                                onActionClick = {})
                        }
                        else if(bookingViewModel?.currentAppointmentBooking?.value?.appointmentTime == null) {
                            ShowSnackBar(title = "No Time Selected",
                                description = "Please Select Appointment Time to proceed",
                                actionLabel = "",
                                duration = StackedSnackbarDuration.Short,
                                snackBarType = SnackBarType.ERROR,
                                stackedSnackBarHostState,
                                onActionClick = {})
                        }
                        else {
                            pagerState.animateScrollToPage(2)
                        }
                    }
                }
            }
        }

    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun AttachBookingPages(pagerState: PagerState, services: Services,onAddMoreServiceClicked:() -> Unit){

        val  boxModifier =
            Modifier
                .padding(top = 5.dp)
                .background(color = Color.White)
                .fillMaxHeight(0.85f)
                .fillMaxWidth()

        // AnimationEffect
        Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> BookingSelectServices(mainViewModel, bookingViewModel!!,services)
                    1 -> BookingSelectSpecialist(mainViewModel,uiStateViewModel!!,bookingViewModel!!,bookingPresenter)
                    2 -> BookingOverview(mainViewModel,uiStateViewModel!!,bookingViewModel!!,bookingPresenter, onAddMoreServiceClicked = {
                         onAddMoreServiceClicked()
                    })
                }
            }

        }

    }
}

class BookingScreenHandler(
    private val bookingViewModel: BookingViewModel,
    private val bookingPresenter: BookingPresenter,
    private val onPageLoading: () -> Unit,
    private val onContentVisible: () -> Unit,
    private val onErrorVisible: () -> Unit,
) : BookingContract.View {
    fun init() {
        bookingPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: UIStates, message: String) {
        uiState.let {
            when{
                it.loadingVisible -> {
                    onPageLoading()
                }

                it.contentVisible -> {
                    onContentVisible()
                }

                it.errorOccurred -> {
                    onErrorVisible()
                }
            }
        }
    }

    override fun showTherapists(serviceSpecialists: List<ServiceTypeSpecialist>) {
        bookingViewModel.setSpecialists(serviceSpecialists)
    }


}



