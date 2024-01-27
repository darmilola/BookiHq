package Models

import kotlinx.datetime.LocalDate


data class AppointmentItemListModel(
    val appointmentItems: List<AppointmentItem>,
    val appointmentDate: LocalDate,
    val appointmentType: Int)


data class AppointmentItem(
    val appointmentId: String = "",
    val spaId: String = "",
    val spaName: String = "",
    val serviceName: String = "",
    val therapistName: String = "",
    val sessionTime: String = "",
    val sessionCharges: String = "",
    val appointmentType: Int
)