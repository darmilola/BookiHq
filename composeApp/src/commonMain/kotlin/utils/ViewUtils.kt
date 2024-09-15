package utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import domain.Enums.CardType
import domain.Enums.DeliveryMethodEnum
import domain.Enums.SessionEnum
import domain.Models.Appointment
import domain.Models.HomepageInfo
import domain.Models.PaymentCard
import domain.Models.PlatformTime
import domain.Models.Product
import domain.Models.ScreenSizeInfo
import domain.Models.Services
import domain.Models.UserAppointment
import domain.Models.VendorStatusModel
import domain.Models.VendorTime
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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
    itemList: List<UserAppointment>
): Int {
    val itemCount = itemList.size

    return itemCount * 200
}

fun getProductViewHeight(
    itemList: List<Product>
): Int {
    val itemCount = itemList.size

    return itemCount * 250
}

fun getServicesViewHeight(
    itemList: List<Services>
): Int {
    if (itemList.size < 4){
        return 140
    }
    val lineCount: Int = ceil((itemList.size/4).toDouble()).toInt()

    return lineCount * 140
}

fun getPaymentCardsViewHeight(
    itemList: List<PaymentCard>
): Int {
    val itemCount = itemList.size
    val size = (itemCount * 100) + 30
    return size
}

fun getPercentOfScreenHeight(
    screenHeight: Dp,
    percentChange: Int
): Int {
    val screenHeightChange = (percentChange.toDouble().div(100.0)) * screenHeight.value
    return screenHeightChange.toInt()
}

fun getCardType(
    startNumber: Int
): String {
   val cardType =  when(startNumber){
        3 -> CardType.AMEX.toPath()
        4 -> CardType.VISA.toPath()
        5 -> CardType.MASTERCARD.toPath()
        2 -> CardType.MASTERCARD.toPath()
       else -> {
           CardType.UNKNOWN.toPath()
       }
   }
   return cardType
}

fun makeValidExpirationText(text: String): String {
    val pattern = Regex("^\\d*\$")
    var validExp = ""
    val singleDigitRange: ArrayList<Int> = arrayListOf()
    singleDigitRange.add(2)
    singleDigitRange.add(3)
    singleDigitRange.add(4)
    singleDigitRange.add(5)
    singleDigitRange.add(6)
    singleDigitRange.add(7)
    singleDigitRange.add(8)
    singleDigitRange.add(9)

    if (text.matches(pattern)){
        //only digit
        val firstText = text[0]
        if (firstText.digitToInt() in singleDigitRange){
            validExp = "0$firstText/"
        }
        else{
            validExp = if (text.length == 2){
                "$text/"
            } else{
                text
            }
        }
    }
    else{
        // has entered month, currently in years 05/ or 12/34
        validExp = if (text == "/") {
            ""
        } else{
            text
        }
    }
    return validExp
}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }


@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

fun calculateHomePageScreenHeight(homepageInfo: HomepageInfo, screenSizeInfo: ScreenSizeInfo, statusList: List<VendorStatusModel>): Int{
    val serviceCount = homepageInfo.vendorServices!!.size
    val recentAppointmentCount = homepageInfo.recentAppointments!!.size
    var statusHeight = 0

    if (statusList.isNotEmpty()){
        statusHeight = 800
    }

    val servicesHeight = (ceil((serviceCount/4).toDouble()) * 140).toInt()
    val recommendationsHeight = 450
    val recentAppointmentHeight = recentAppointmentCount * 200
    val bottomBarPadding = 200

    return servicesHeight + recentAppointmentHeight + recommendationsHeight + statusHeight + bottomBarPadding
}


fun calculateTherapistServiceTimes(platformTimes: List<PlatformTime>, vendorTimes: List<VendorTime>, bookedAppointment: List<Appointment>):
        Triple<ArrayList<PlatformTime>,ArrayList<PlatformTime>,ArrayList<PlatformTime>>{

    val morningHours: ArrayList<PlatformTime> = arrayListOf()
    val afternoonHours: ArrayList<PlatformTime> = arrayListOf()
    val eveningHours: ArrayList<PlatformTime> = arrayListOf()
    val workingHours: ArrayList<PlatformTime> = arrayListOf()
    val vendorWorkingHours: ArrayList<Int> = arrayListOf()
    val bookedHours: ArrayList<Int> = arrayListOf()

    runBlocking {
        vendorTimes.forEach {
            vendorWorkingHours.add(it.platformTime?.id!!)
        }
        bookedAppointment.forEach {
            bookedHours.add(it.platformTime.id!!)
        }

        platformTimes.map {
            if (it.id in vendorWorkingHours && it.id !in bookedHours) {
                workingHours.add(it.copy(isEnabled = true))
            } else {
                workingHours.add(it)
            }
        }
        workingHours.map {
            when (it.session) {
                SessionEnum.MORNING.toPath() -> {
                    morningHours.add(it)
                }

                SessionEnum.AFTERNOON.toPath() -> {
                    afternoonHours.add(it)
                }

                else -> {
                    eveningHours.add(it)
                }
            }
        }
    }

    return Triple(morningHours, afternoonHours, eveningHours)

}

fun calculatePackageServiceTimes(platformTimes: List<PlatformTime>, vendorTimes: List<VendorTime>):
        Triple<ArrayList<PlatformTime>,ArrayList<PlatformTime>,ArrayList<PlatformTime>>{

    val morningHours: ArrayList<PlatformTime> = arrayListOf()
    val afternoonHours: ArrayList<PlatformTime> = arrayListOf()
    val eveningHours: ArrayList<PlatformTime> = arrayListOf()
    val workingHours: ArrayList<PlatformTime> = arrayListOf()
    val vendorWorkingHours: ArrayList<Int> = arrayListOf()
    val bookedHours: ArrayList<Int> = arrayListOf()

    runBlocking {
        vendorTimes.forEach {
            vendorWorkingHours.add(it.platformTime?.id!!)
        }

        platformTimes.map {
            if (it.id in vendorWorkingHours) {
                workingHours.add(it.copy(isEnabled = true))
            } else {
                workingHours.add(it)
            }
        }
        workingHours.map {
            when (it.session) {
                SessionEnum.MORNING.toPath() -> {
                    morningHours.add(it)
                }

                SessionEnum.AFTERNOON.toPath() -> {
                    afternoonHours.add(it)
                }

                else -> {
                    eveningHours.add(it)
                }
            }
        }
    }

    return Triple(morningHours, afternoonHours, eveningHours)

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

fun getDeliveryMethodDisplayName(deliveryMethod: String): String {
    return when(deliveryMethod){
        DeliveryMethodEnum.MOBILE.toPath() -> "Mobile Delivery"
        DeliveryMethodEnum.PICKUP.toPath() -> "Pickup"
        else -> "Pickup"
    }
}


fun calculateStatusViewHeightPercent(height: Int, width: Int): Double {
    println("Height is $height width is $width")
    var heightRatio = 0.50 // initial height ratio
    if (height < width){
        val difference = width - height // how much width greater than the height
        var ratio: Double = difference.toDouble()/width.toDouble() // how much width greater than the height
        if (ratio > 0.80){
            ratio = 0.38  // 47% + 50% width more than height
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else if (ratio > 0.76){
            ratio = 0.37  // 37% width more than height
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else if (ratio > 0.70){
            ratio = 0.36  // 35% width more than height
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else if (ratio > 0.66){
            ratio = 0.34  // 30% width more than height
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else if (ratio > 0.60){
            ratio = 0.32  // 25% width more than height
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else if (ratio > 0.56){
            ratio = 0.30 // 20%  width more than height
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else if (ratio > 0.50){
            ratio = 0.28  // 15%  width more than height
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else if (ratio > 0.46){
            ratio = 0.24  // 10%  width more than height Almost Square
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else if (ratio > 0.40){
            ratio = 0.22  // 10%  width more than height Almost Square
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else if (ratio > 0.36){
            ratio = 0.20  // 8%  width more than height
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else if (ratio > 0.30){
            ratio = 0.18  // 8%  width more than height
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else if (ratio > 0.26){
            ratio = 0.16  // 6%  width more than height
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else if (ratio > 0.20){
            ratio = 0.14  // 6%  width more than height
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else if (ratio > 0.16){
            ratio = 0.12  // 4%  width more than height
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else if (ratio > 0.10){
            ratio = 0.10  // 2%  width more than height
            heightRatio = 0.5 - ratio
            return heightRatio
        }
        else{
            heightRatio = 0.5 - ratio
        }


    }
    else if (height > width) {
        val difference = height - width // how much height greater than the width
        val ratio: Double = difference.toDouble()/height.toDouble()
        println("mRatio is $ratio")
        heightRatio = if (ratio > 0.30){
            ratio + 0.47 // height grow 47% + changes  more than width 50% is the meeting point
        } else {
            ratio + 0.5 // height grow 50% + changes  more than width 50% is the meeting point
        }
        return if (heightRatio > 1.00){  //Perfect Portrait Ratio
            1.00
        } else {
            heightRatio
        }
    }
    else{
        heightRatio = 0.50
    }

    return heightRatio
}

fun getDateTimeFromTimeStamp(timeStamp: Long): LocalDateTime {
    val dt = Instant.fromEpochSeconds(timeStamp)
        .toLocalDateTime(TimeZone.currentSystemDefault())
    return dt
}

fun getStatusDisplayTime(localDateTime: LocalDateTime): String {
    val hour = localDateTime.hour
    val minute = localDateTime.minute
    val minuteText = if (minute < 10) "0$minute" else minute.toString()
    val currentMoment: Instant = Clock.System.now()
    var isAm: Boolean
    var displayHour = 0
    val today: LocalDate = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault()).date
    val isToday = today == localDateTime.date
    if (hour > 12) {
        isAm = false
        displayHour = hour - 12
    } else {
        isAm = true
        displayHour = hour
    }
    val todayText = if (isToday) {
        "Today"
    } else {
        "Yesterday"
    }
    val meridian = if (isAm) "AM" else "PM"
    return "$displayHour:$minuteText$meridian, $todayText"
}

