package utils

import domain.Models.Appointment
import domain.Models.AppointmentItem
import domain.Models.Product
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

