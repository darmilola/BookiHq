package utils

import androidx.compose.ui.unit.Dp
import domain.Models.Appointment
import domain.Models.HomepageInfo
import domain.Models.Product
import domain.Models.Services
import domain.Models.UnsavedAppointment

fun getAppointmentViewHeight(
        itemList: List<Appointment>
    ): Int {
        val itemCount = itemList.size

        return itemCount * 200
    }



fun getUnSavedAppointmentViewHeight(
    itemList: List<UnsavedAppointment>
): Int {
    val itemCount = itemList.size

    return itemCount * 190
}

fun getPopularProductViewHeight(
    itemList: List<Product>
): Int {
    val itemCount = itemList.size

    return itemCount * 225
}

fun getServicesViewHeight(
    itemList: List<Services>
): Int {
    val lineCount: Int = itemList.size/4

    return lineCount * 140
}

fun getPercentOfScreenHeight(
    screenHeight: Dp,
    percentChange: Int
): Int {
    val screenHeightChange = (percentChange.toDouble().div(100.0)) * screenHeight.value
    return screenHeightChange.toInt()
}

fun calculateHomePageScreenHeight(homepageInfo: HomepageInfo): Int{
    val serviceCount = homepageInfo.vendorServices!!.size
    val recommendationsCount = homepageInfo.recommendationRecommendations!!.size
    val recentAppointmentCount = homepageInfo.recentAppointment!!.size
    val popularProductsCount = homepageInfo.popularProducts!!.size


    val servicesHeight = serviceCount * 140
    val recommendationsHeight = 400
    val recentAppointmentHeight = recentAppointmentCount * 180
    val popularProductsHeight = popularProductsCount * 220

    return servicesHeight + recentAppointmentHeight + recommendationsHeight + popularProductsHeight
}

