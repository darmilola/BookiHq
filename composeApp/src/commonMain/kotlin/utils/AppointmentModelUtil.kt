package utils

import domain.Models.AppointmentItem

fun getAppointmentViewHeight(
        itemList: List<AppointmentItem>
    ): Int {
        val itemCount = itemList.size

        return itemCount * 220
    }

