package presentation.appointmentBookings

import GGSansSemiBold
import StackedSnackbarHost
import theme.styles.Colors
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import applications.date.getDay
import applications.date.getMonth
import applications.date.getYear
import domain.Enums.Screens
import domain.Models.Appointment
import domain.Models.ServiceTypeItem
import domain.Models.Services
import drawable.ErrorOccurredWidget
import presentation.components.IndeterminateCircularProgressBar
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.widgets.BookingCalendar
import presentation.widgets.DropDownWidget
import presentation.widgets.EmptyContentWidget
import presentation.widgets.ServiceLocationToggle
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState

@Composable
fun BookingSelectServices(mainViewModel: MainViewModel, bookingViewModel: BookingViewModel,
                          performedActionUIStateViewModel: PerformedActionUIStateViewModel,
                          services: Services, bookingPresenter: BookingPresenter) {

    LaunchedEffect(Unit, block = {
        if (bookingViewModel.serviceTypeList.value.isEmpty()) {
            bookingPresenter.getServiceTypes(services.serviceId)
        }
    })

    val mobileServicesAvailable = mainViewModel.connectedVendor.value.isMobileServiceAvailable
    val isServiceTypeMobileServiceAvailable = remember { mutableStateOf(false) }

    val getServiceTypeActionUiStates =
        performedActionUIStateViewModel.getServiceTypeUiState.collectAsState()

    if (getServiceTypeActionUiStates.value.isLoading) {
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            IndeterminateCircularProgressBar()
        }
    } else if (getServiceTypeActionUiStates.value.isFailed) {
        Box(
            modifier = Modifier.fillMaxWidth().height(400.dp),
            contentAlignment = Alignment.Center
        ) {
            ErrorOccurredWidget(getServiceTypeActionUiStates.value.errorMessage, onRetryClicked = {
                bookingPresenter.getServiceTherapists(
                    bookingViewModel.selectedServiceType.value.serviceTypeId,
                    mainViewModel.connectedVendor.value.vendorId!!,
                    day = bookingViewModel.day.value,
                    month = bookingViewModel.month.value,
                    year = bookingViewModel.year.value
                )
            })
        }
    } else if (getServiceTypeActionUiStates.value.isEmpty) {
        Box(
            modifier = Modifier.fillMaxWidth().height(400.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyContentWidget(emptyText = getServiceTypeActionUiStates.value.emptyMessage)
            }
        }
    } else if (getServiceTypeActionUiStates.value.isSuccess) {

        val currentBooking = Appointment()
        currentBooking.services = services
        currentBooking.serviceId = services.serviceId

        currentBooking.appointmentYear = getYear()
        currentBooking.appointmentMonth = getMonth()
        currentBooking.appointmentDay = getDay()

        bookingViewModel.setCurrentAppointmentBooking(currentBooking)
        bookingViewModel.setSelectedDay(currentBooking.appointmentDay!!)
        bookingViewModel.setSelectedMonth(currentBooking.appointmentMonth!!)
        bookingViewModel.setSelectedYear(currentBooking.appointmentYear!!)

        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        val boxModifier =
            Modifier
                .height(350.dp)
                .fillMaxWidth()
        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) }
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                // AnimationEffect
                Box(contentAlignment = Alignment.TopStart, modifier = boxModifier) {
                    AttachServiceImages(mainViewModel)
                }
                ServiceTitle(mainViewModel)
                AttachServiceTypeToggle(mainViewModel, bookingViewModel, onServiceSelected = {
                    val description =
                        if (mainViewModel.currentUserInfo.value.isProfileCompleted == true) {
                            "You can go Mobile for this service"
                        } else {
                            "Please complete your profile"
                        }
                    val action =
                        if (mainViewModel.currentUserInfo.value.isProfileCompleted == true) {
                            ""
                        } else {
                            "Complete"
                        }
                    if (it.mobileServiceAvailable) {
                        isServiceTypeMobileServiceAvailable.value = true
                        ShowSnackBar(title = "Mobile Service Is Available",
                            description = description,
                            actionLabel = action,
                            duration = StackedSnackbarDuration.Short,
                            snackBarType = SnackBarType.INFO,
                            stackedSnackBarHostState,
                            onActionClick = {
                                mainViewModel.setScreenNav(
                                    Pair(
                                        Screens.BOOKING.toPath(),
                                        Screens.EDIT_PROFILE.toPath()
                                    )
                                )

                            })
                    } else {
                        isServiceTypeMobileServiceAvailable.value = false
                    }
                    if (it.serviceTypeId != -1L) {
                        bookingViewModel.undoSelectedServiceType()
                        bookingViewModel.setSelectedServiceType(it)
                        currentBooking.serviceTypeItem = it
                        currentBooking.serviceTypeId = it.serviceTypeId
                        bookingViewModel.setCurrentAppointmentBooking(currentBooking)
                    }
                })
                if (mobileServicesAvailable) {
                    val isServiceLocationDisabled =
                        mainViewModel.currentUserInfo.value.isProfileCompleted != true && !isServiceTypeMobileServiceAvailable.value
                    ServiceLocationToggle(
                        bookingViewModel,
                        isDisabled = isServiceLocationDisabled,
                        onSpaSelectedListener = {
                            currentBooking.isMobileService = false
                            bookingViewModel.setIsMobileService(false)
                            bookingViewModel.setCurrentAppointmentBooking(currentBooking)
                        },
                        onMobileSelectedListener = {
                            currentBooking.isMobileService = true
                            bookingViewModel.setIsMobileService(true)
                            bookingViewModel.setCurrentAppointmentBooking(currentBooking)
                        })
                }
                BookingCalendar {
                    bookingViewModel.undoTherapists()
                    currentBooking.appointmentDay = it.dayOfMonth
                    currentBooking.appointmentMonth = it.monthNumber
                    currentBooking.appointmentYear = it.year
                    bookingViewModel.setCurrentAppointmentBooking(currentBooking)
                    bookingViewModel.setSelectedDay(it.dayOfMonth)
                    bookingViewModel.setSelectedMonth(it.monthNumber)
                    bookingViewModel.setSelectedYear(it.year)
                    bookingViewModel.setSelectedMonthName(it.month.name)
                }
            }
        }
    }
}

@Composable
fun AttachServiceTypeToggle(mainViewModel: MainViewModel,bookingViewModel: BookingViewModel, onServiceSelected: (ServiceTypeItem) -> Unit){
    Column(
        modifier = Modifier
            .padding(top = 35.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "What type of Service do you want?",
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 10.dp))
        AttachServiceDropDownWidget(mainViewModel,bookingViewModel, onServiceSelected = {
               onServiceSelected(it)
        })
    }
}

@Composable
fun AttachServiceDropDownWidget(mainViewModel: MainViewModel, bookingViewModel: BookingViewModel, onServiceSelected: (ServiceTypeItem) -> Unit) {
    val serviceTypes = bookingViewModel.serviceTypeList.collectAsState()
    val recommendationServiceType = mainViewModel.recommendedServiceType.value
    val isRecommendationType = recommendationServiceType.serviceTypeId != -1L
    val serviceTypeList = arrayListOf<String>()
    var selectedIndex: Int = -1
    if (recommendationServiceType.serviceTypeId != -1L){
        serviceTypeList.add(recommendationServiceType.title)
        selectedIndex = 0
    }
    var selectedService: ServiceTypeItem? = null
    for (item in bookingViewModel.serviceTypeList.value){
         serviceTypeList.add(item.title)
    }
    DropDownWidget(menuItems = serviceTypeList, selectedIndex = selectedIndex, shape = CircleShape ,iconRes = "drawable/spa_treatment_leaves.png",
        placeHolderText = "Select Service Type", iconSize = 20, onMenuItemClick = {
        if (!isRecommendationType) {
            selectedService = serviceTypes.value[it]
            onServiceSelected(selectedService!!)
        }
      else{
           onServiceSelected(recommendationServiceType)
      }
    })
}

@Composable
fun ServiceTitle(mainViewModel: MainViewModel){
    val serviceState = mainViewModel.selectedService.collectAsState()
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = serviceState.value.serviceInfo?.title!!,
            fontSize = 20,
            fontFamily = GGSansSemiBold,
            textStyle =  MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth()
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AttachServiceImages(mainViewModel: MainViewModel){
    val serviceState = mainViewModel.selectedService.collectAsState()
    val serviceImages = serviceState.value.serviceImages
    val pagerState = rememberPagerState(pageCount = {
        serviceImages.size
    })

    val  boxModifier =
        Modifier
            .padding(bottom = 20.dp)
            .fillMaxHeight()
            .fillMaxWidth()

    // AnimationEffect
    Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            ImageComponent(imageModifier = Modifier.fillMaxWidth().height(350.dp), imageRes = serviceImages[page].imageUrl!!, isAsync = true ,contentScale = ContentScale.Crop)
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color: Color
                val width: Int
                if (pagerState.currentPage == iteration){
                    color =  Colors.primaryColor
                    width = 25
                } else{
                    color =  Colors.lightPrimaryColor
                    width = 25
                }
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .height(2.dp)
                        .width(width.dp)
                )
            }

        }
    }

}
