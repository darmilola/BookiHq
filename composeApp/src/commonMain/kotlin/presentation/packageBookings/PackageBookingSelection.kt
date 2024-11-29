package presentation.packageBookings

import GGSansSemiBold
import StackedSnackbarHost
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import applications.date.getDay
import applications.date.getMonth
import applications.date.getYear
import domain.Models.Appointment
import domain.Models.PlatformTime
import domain.Models.VendorPackage
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.BookingCalendar
import presentation.widgets.ServiceLocationToggle
import presentation.widgets.TimeGridDisplay
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors
import utils.calculatePackageServiceTimes


@Composable
fun PackageBookingSelection(mainViewModel: MainViewModel, bookingViewModel: BookingViewModel,
                           vendorPackage: VendorPackage) {

    val mobileServicesAvailable = mainViewModel.connectedVendor.value.isMobileServiceAvailable
    val isPackageMobileServiceAvailable = vendorPackage.isMobileServiceAvailable
    val userProfile = mainViewModel.currentUserInfo.value
    val isProfileCompleted = userProfile.address.trim().isNotEmpty() && userProfile.contactPhone.trim().isNotEmpty()

    val currentBooking = Appointment()
    currentBooking.packageInfo = vendorPackage
    currentBooking.packageId = vendorPackage.packageId

    currentBooking.appointmentYear = getYear()
    currentBooking.appointmentMonth = getMonth()
    currentBooking.appointmentDay = getDay()

    val vendorTimes = bookingViewModel.vendorTimes.collectAsState()
    val platformTimes = bookingViewModel.platformTimes.collectAsState()


    bookingViewModel.setCurrentAppointmentBooking(currentBooking)
    bookingViewModel.setSelectedDay(currentBooking.appointmentDay!!)
    bookingViewModel.setSelectedMonth(currentBooking.appointmentMonth!!)
    bookingViewModel.setSelectedYear(currentBooking.appointmentYear!!)

    val stackedSnackBarHostState = rememberStackedSnackbarHostState(
        maxStack = 5,
        animation = StackedSnackbarAnimation.Bounce
    )

    Scaffold(
        snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {
            PackageTitle(vendorPackage)
            if (mobileServicesAvailable) {
                val isServiceLocationDisabled = !isPackageMobileServiceAvailable || !isProfileCompleted
                ServiceLocationToggle(
                    bookingViewModel,
                    isPackage = true,
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
           if (platformTimes.value.isNotEmpty()) {

               val workHours = calculatePackageServiceTimes(
                   platformTimes = platformTimes.value,
                   vendorTimes = vendorTimes.value
               )
               Column(
                   modifier = Modifier
                       .padding(top = 5.dp, bottom = 10.dp)
                       .fillMaxWidth()
                       .height(400.dp),
                   verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally,
               ) {

                   AvailableTimeContent(
                       workHours,
                       onWorkHourClickListener = {
                           currentBooking.pendingTime = it
                           bookingViewModel.setCurrentAppointmentBooking(currentBooking)
                       })
               }
           }

        }
    }
}

@Composable
fun AvailableTimeContent(availableHours: Triple<ArrayList<PlatformTime>, ArrayList<PlatformTime>, ArrayList<PlatformTime>>, onWorkHourClickListener: (PlatformTime) -> Unit) {
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 10.dp)
            .height(400.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Available Times",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 10.dp, bottom = 20.dp))
        Row(modifier = Modifier.fillMaxWidth(0.90f).wrapContentHeight()) {
            TextComponent(
                text = "Morning",
                fontSize = 16,
                textStyle = TextStyle(),
                textColor = theme.Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.weight(1f).padding(bottom = 15.dp, top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            TextComponent(
                text = "Afternoon",
                fontSize = 16,
                textStyle = TextStyle(),
                textColor = theme.Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.weight(1f).padding(bottom = 15.dp, top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            TextComponent(
                text = "Evening",
                fontSize = 16,
                textStyle = TextStyle(),
                textColor = theme.Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.weight(1f).padding(bottom = 15.dp, top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
        }

        Row(modifier = Modifier.fillMaxWidth().height(400.dp)) {
            Column(modifier = Modifier.fillMaxWidth().height(400.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                TimeGridDisplay(platformTimes = availableHours, onWorkHourClickListener = {
                    onWorkHourClickListener(it)
                })
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
