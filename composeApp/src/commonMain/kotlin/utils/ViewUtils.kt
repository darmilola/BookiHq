package utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import domain.Models.Appointment
import domain.Models.BookedTimes
import domain.Models.HomepageInfo
import domain.Models.PlatformTime
import domain.Models.Product
import domain.Models.ScreenSizeInfo
import domain.Models.Services
import domain.Models.VendorStatusModel
import domain.Models.VendorTime
import kotlin.math.ceil

fun getAppointmentViewHeight(
        itemListCount: Int
    ): Int {

        return itemListCount * 280
    }

fun getOrderViewHeight(
    itemCount: Int
): Int{

    return itemCount * 350
}

fun getRecentAppointmentViewHeight(
    itemList: List<Appointment>
): Int {
    val itemCount = itemList.size

    return itemCount * 200
}

fun getPopularProductViewHeight(
    itemList: List<Product>
): Int {
    val itemCount = itemList.size

    return itemCount * 250
}

fun getServicesViewHeight(
    itemList: List<Services>
): Int {
    val lineCount: Int = ceil((itemList.size/4).toDouble()).toInt()

    return lineCount * 140
}

fun getPercentOfScreenHeight(
    screenHeight: Dp,
    percentChange: Int
): Int {
    val screenHeightChange = (percentChange.toDouble().div(100.0)) * screenHeight.value
    return screenHeightChange.toInt()
}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }


@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

fun calculateHomePageScreenHeight(homepageInfo: HomepageInfo, screenSizeInfo: ScreenSizeInfo, statusList: List<VendorStatusModel>): Int{
    val serviceCount = homepageInfo.vendorServices!!.size
    val pastAppointmentCount = homepageInfo.pastAppointment!!.size
    val upcomingAppointmentCount = homepageInfo.upcomingAppointment!!.size
    var statusHeight = 0

    if (statusList.isNotEmpty()){
        statusHeight = 800
    }

    val servicesHeight = (ceil((serviceCount/4).toDouble()) * 140).toInt()
    val recommendationsHeight = 450
    val pastAppointmentHeight = pastAppointmentCount * 200
    val upcomingAppointmentHeight = upcomingAppointmentCount * 200

    return servicesHeight + pastAppointmentHeight + recommendationsHeight + statusHeight + upcomingAppointmentHeight
}

fun calculateBookingServiceTimes(platformTimes: List<PlatformTime>, vendorTimes: List<VendorTime>, bookedTimes: List<BookedTimes>, day: Int, month: Int, year: Int):
ArrayList<PlatformTime>{
    val workingHours: ArrayList<PlatformTime> = arrayListOf()
    val vendorWorkingHours: ArrayList<Int> = arrayListOf()
    val bookedHours: ArrayList<Int> = arrayListOf()

    vendorTimes.forEach {
        vendorWorkingHours.add(it.platformTime?.id!!)
    }
    bookedTimes.forEach {
        if (it.day == day && it.month == month && it.year == year) {
            bookedHours.add(it.platformTime?.id!!)
        }
    }


    platformTimes.map {
        if (it.id in vendorWorkingHours && it.id !in bookedHours){
            workingHours.add(it.copy(isEnabled = true))
        }
        else{
            workingHours.add(it)
        }
    }

    return workingHours

}


fun calculateTherapistServiceTimes(platformTimes: List<PlatformTime>, vendorTimes: List<VendorTime>, bookedAppointment: List<Appointment>):
        ArrayList<PlatformTime>{


    val workingHours: ArrayList<PlatformTime> = arrayListOf()
    val vendorWorkingHours: ArrayList<Int> = arrayListOf()
    val bookedHours: ArrayList<Int> = arrayListOf()

    vendorTimes.forEach {
        vendorWorkingHours.add(it.platformTime?.id!!)
    }
       bookedAppointment.forEach {
           bookedHours.add(it.platformTime?.id!!)
        }

    platformTimes.map {
        if (it.id in vendorWorkingHours && it.id !in bookedHours){
            workingHours.add(it.copy(isEnabled = true))
        }
        else{
            workingHours.add(it)
        }
    }

    return workingHours

}


fun calculateVendorServiceTimes(platformTimes: List<PlatformTime>, vendorTimes: List<VendorTime>):
        ArrayList<PlatformTime>{


    val workingHours: ArrayList<PlatformTime> = arrayListOf()
    val vendorWorkingHours: ArrayList<Int> = arrayListOf()

    vendorTimes.forEach {
        vendorWorkingHours.add(it.platformTime?.id!!)
    }

    platformTimes.map {
        if (it.id in vendorWorkingHours){
            workingHours.add(it.copy(isEnabled = true))
        }
        else{
            workingHours.add(it)
        }
    }

    return workingHours

}

