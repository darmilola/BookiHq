package presentation.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.Models.Appointment
import domain.Enums.ServiceStatusEnum
import domain.Models.UserAppointmentsData
import presentation.appointments.AppointmentPresenter
import theme.styles.Colors


@Composable
fun TherapistAppointmentWidget(userAppointmentsData: UserAppointmentsData, appointmentPresenter: AppointmentPresenter? = null) {

    val appointmentStatus = userAppointmentsData.resources?.serviceStatus
    val menuItems = arrayListOf<String>()

    var actionItem = ""
    actionItem = when (appointmentStatus) {
        ServiceStatusEnum.PENDING.toPath() -> {
            "Postpone"
        }

        else -> {
            "Delete"
        }
    }

    menuItems.add(actionItem)
    if (appointmentStatus == ServiceStatusEnum.DONE.toPath()){
        menuItems.add("Add Review")
    }


    var iconRes = "drawable/schedule.png"
    var statusText = "Pending"
    var statusColor: Color = Colors.primaryColor



    when (appointmentStatus) {
        ServiceStatusEnum.PENDING.toPath() -> {
            iconRes = "drawable/schedule.png"
            statusText = "Pending"
            statusColor = Colors.primaryColor
        }
        ServiceStatusEnum.POSTPONED.toPath() -> {
            iconRes = "drawable/appointment_postponed.png"
            statusText = "Postponed"
            statusColor = Colors.pinkColor
        }
        ServiceStatusEnum.DONE.toPath() -> {
            iconRes = "drawable/appointment_done.png"
            statusText = "Done"
            statusColor = Colors.greenColor
        }
    }


    val boxBgModifier =
        Modifier
            .padding(bottom = 5.dp, top = 5.dp, start = 10.dp, end = 10.dp)
            .fillMaxHeight()
            .fillMaxWidth()
            .border(border = BorderStroke(1.4.dp, Colors.lightGray), shape = RoundedCornerShape(10.dp))

    Box(modifier = boxBgModifier) {

        val columnModifier = Modifier
            .padding(start = 5.dp, bottom = 10.dp)
            .fillMaxWidth()
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = columnModifier
        ) {
            ///AttachAppointmentHeader(statusText, iconRes, statusColor, appointment, menuItems, appointmentPresenter)
            AttachTherapistAppointmentContent(userAppointmentsData.resources!!)
        }
    }
}


@Composable
fun AttachTherapistAppointmentContent(appointment: Appointment) {

    AppointmentInfoWidget(appointment)
    Divider(color = Color(color = 0x90C8C8C8), thickness = 1.dp, modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, bottom = 10.dp, top = 20.dp))
    CustomerInfoWidget(appointment)

}






