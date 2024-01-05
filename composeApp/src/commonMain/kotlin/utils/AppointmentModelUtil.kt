package utils

import Models.AppointmentItem
import Models.AppointmentItemListModel
 fun getAppointmentViewHeight(
        itemList: List<AppointmentItem>
    ): Int {
        val itemCount = itemList.size

        return itemCount * 230
    }

