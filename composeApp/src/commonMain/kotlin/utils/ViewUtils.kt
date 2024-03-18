package utils

import domain.Models.AppointmentItem
import domain.Models.Product
import domain.Models.UnsavedAppointment

fun getAppointmentViewHeight(
        itemList: List<AppointmentItem>
    ): Int {
        val itemCount = itemList.size

        return itemCount * 190
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

