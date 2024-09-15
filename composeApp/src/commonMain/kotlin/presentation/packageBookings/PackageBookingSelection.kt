package presentation.packageBookings

import GGSansSemiBold
import StackedSnackbarHost
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
import domain.Models.Appointment
import domain.Models.ServiceTypeItem
import domain.Models.VendorPackage
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.BookingCalendar
import presentation.widgets.DropDownWidget
import presentation.widgets.ServiceLocationToggle
import presentations.components.ImageComponent
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors


@Composable
fun PackageBookingSelection(mainViewModel: MainViewModel, bookingViewModel: BookingViewModel,
                           vendorPackage: VendorPackage) {

    val mobileServicesAvailable = mainViewModel.connectedVendor.value.isMobileServiceAvailable
    val isPackageMobileServiceAvailable = vendorPackage.isMobileServiceAvailable

    val currentBooking = Appointment()
    currentBooking.packages = vendorPackage
    currentBooking.packageId = vendorPackage.packageId

    currentBooking.appointmentYear = getYear()
    currentBooking.appointmentMonth = getMonth()
    currentBooking.appointmentDay = getDay()

    bookingViewModel.setCurrentBooking(currentBooking)
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
            
            Box(contentAlignment = Alignment.TopStart, modifier = boxModifier) {
                AttachPackageImages(vendorPackage)
            }
            PackageTitle(vendorPackage)
            if (mobileServicesAvailable) {
                val isServiceLocationDisabled =
                    mainViewModel.currentUserInfo.value.isProfileCompleted != true && !isPackageMobileServiceAvailable
                ServiceLocationToggle(
                    bookingViewModel,
                    isDisabled = isServiceLocationDisabled,
                    onSpaSelectedListener = {
                        currentBooking.isMobileService = false
                        bookingViewModel.setIsMobileService(false)
                        bookingViewModel.setCurrentBooking(currentBooking)
                    },
                    onMobileSelectedListener = {
                        currentBooking.isMobileService = true
                        bookingViewModel.setIsMobileService(true)
                        bookingViewModel.setCurrentBooking(currentBooking)
                    })
            }
            BookingCalendar {
                bookingViewModel.undoTherapists()
                currentBooking.appointmentDay = it.dayOfMonth
                currentBooking.appointmentMonth = it.monthNumber
                currentBooking.appointmentYear = it.year
                bookingViewModel.setCurrentBooking(currentBooking)
                bookingViewModel.setSelectedDay(it.dayOfMonth)
                bookingViewModel.setSelectedMonth(it.monthNumber)
                bookingViewModel.setSelectedYear(it.year)
                bookingViewModel.setSelectedMonthName(it.month.name)
            }
        }
    }
}

@Composable
fun PackageTitle(vendorPackage: VendorPackage){
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = vendorPackage.title,
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
fun AttachPackageImages(vendorPackage: VendorPackage){
    val packageImages = vendorPackage.packageImages
    val pagerState = rememberPagerState(pageCount = {
        packageImages.size
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
            ImageComponent(imageModifier = Modifier.fillMaxWidth().height(350.dp), imageRes = packageImages[page].imageUrl, isAsync = true ,contentScale = ContentScale.Crop)
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
