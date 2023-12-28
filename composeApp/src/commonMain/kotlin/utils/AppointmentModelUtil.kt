package utils

import Models.AppointmentItemListModel
 fun getAppointmentViewHeight(
        itemList: List<AppointmentItemListModel>
    ): Int {
        val itemCount = itemList.size
        var subItemCount = 0
        for (item in itemList) {
            subItemCount += item.appointmentItems.size
        }
        return (subItemCount * 145) + (itemCount * 100)
    }

