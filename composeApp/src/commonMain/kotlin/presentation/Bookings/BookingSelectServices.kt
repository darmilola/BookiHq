package presentation.Bookings

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.Models.ServiceTypeItem
import domain.Models.ServiceImages
import domain.Models.Services
import domain.Models.UnsavedAppointment
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.BookingCalendar
import presentation.widgets.DropDownWidget
import presentation.widgets.ServiceLocationToggle
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState

@Composable
fun BookingSelectServices(mainViewModel: MainViewModel,bookingViewModel: BookingViewModel,
                          services: Services) {

    //Initialising New Booking
    var currentBookingId = -1
    val savedBooking = bookingViewModel.currentAppointmentBooking.value

    if(bookingViewModel.currentBookingId.value == -1) {
        currentBookingId = (0..100000000).random()
        bookingViewModel.setCurrentBookingId(currentBookingId)
    }
    else{
        currentBookingId = bookingViewModel.currentBookingId.value
    }
    val currentBooking =  if (savedBooking.bookingId != -1) savedBooking else UnsavedAppointment(currentBookingId)
    currentBooking.services = services
    currentBooking.serviceId = services.serviceId

    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )

    // initialise new recommendation
    if (mainViewModel.vendorRecommendation.value.recommendationId != -1){
        val recommendation = mainViewModel.vendorRecommendation.value
        val serviceType = recommendation.serviceTypeItem
        bookingViewModel.setSelectedServiceType(serviceType!!)
        currentBooking.serviceTypeItem = serviceType
        currentBooking.serviceTypeId = serviceType.categoryId
        bookingViewModel.setCurrentBooking(currentBooking)
        if (services.serviceTypes.size == 0) {
            services.serviceTypes.add(serviceType)
        }
    }

    val boxModifier =
        Modifier
            .height(350.dp)
            .fillMaxWidth()
    Scaffold(
        snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState)  }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // AnimationEffect
            Box(contentAlignment = Alignment.TopStart, modifier = boxModifier) {
                AttachServiceImages(services.serviceImages)
            }
            ServiceTitle(services.serviceTitle)
            AttachServiceTypeToggle(services,bookingViewModel, onServiceSelected = {
                if (!it.homeServiceAvailable) {
                    ShowSnackBar(title = "No Home Service",
                        description = "Home service is not available for the selected service",
                        actionLabel = "",
                        duration = StackedSnackbarDuration.Short,
                        snackBarType = SnackBarType.ERROR,
                        stackedSnackBarHostState,
                        onActionClick = {})
                }
                if (it.serviceId != -1) {
                    bookingViewModel.setSelectedServiceType(it)
                    currentBooking.serviceTypeItem = it
                    currentBooking.serviceTypeId = it.categoryId
                    bookingViewModel.setCurrentBooking(currentBooking)
                }
            })
            ServiceLocationToggle(bookingViewModel, mainViewModel, onSpaSelectedListener = {
                currentBooking.isHomeService = false
                bookingViewModel.setCurrentBooking(currentBooking)
            }, onHomeSelectedListener = {
                currentBooking.isHomeService = true
                bookingViewModel.setCurrentBooking(currentBooking)
            })
            BookingCalendar(bookingViewModel = bookingViewModel) {
                currentBooking.appointmentDate = it
                bookingViewModel.setCurrentBooking(currentBooking)
                bookingViewModel.setSelectedDate(it)
            }
        }
    }
}

@Composable
fun AttachServiceTypeToggle(services: Services,bookingViewModel: BookingViewModel, onServiceSelected: (ServiceTypeItem) -> Unit){
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 35.dp)
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
        AttachDropDownWidget(services, bookingViewModel = bookingViewModel, onServiceSelected = {
               onServiceSelected(it)
        })
    }
}

@Composable
fun AttachDropDownWidget(services: Services, bookingViewModel: BookingViewModel, onServiceSelected: (ServiceTypeItem) -> Unit) {
    val serviceTypeList = arrayListOf<String>()
    var selectedIndex: Int = -1
    val unsavedAppointment = bookingViewModel.currentAppointmentBooking.value
    if (unsavedAppointment.serviceTypeId != -1) {
        selectedIndex = services.serviceTypes.indexOf(unsavedAppointment.serviceTypeItem!!)
    }
    var selectedService: ServiceTypeItem? = null
    for (item in services.serviceTypes){
         serviceTypeList.add(item.title)
    }
    DropDownWidget(menuItems = serviceTypeList, selectedIndex = selectedIndex, iconRes = "drawable/spa_treatment_leaves.png",
        placeHolderText = "Select Service Type", iconSize = 25, onMenuItemClick = {
         selectedService = services.serviceTypes[it]
         onServiceSelected(selectedService!!)
    })
}

@Composable
fun ServiceTitle(serviceTitle: String){
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = serviceTitle,
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
fun AttachServiceImages(serviceImages: ArrayList<ServiceImages>){

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
                        .height(3.dp)
                        .width(width.dp)
                )
            }

        }
    }

}
