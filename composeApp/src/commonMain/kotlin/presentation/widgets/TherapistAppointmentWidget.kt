package presentation.widgets

import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Models.Appointment
import domain.Enums.BookingStatus
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors

@Composable
fun AttachTherapistAppointmentHeader(statusText: String, statusDrawableRes: String, statusColor: Color, appointment: Appointment, onArchiveAppointment: (Appointment) -> Unit,
                                     onUpdateToDone: (Appointment) -> Unit) {

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 5.dp, top = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(0.70f).height(40.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth(0.08f).fillMaxHeight(), contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(color = statusColor)){}
            }
            TextComponent(
                text = appointment.services?.serviceInfo?.title.toString(),
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 30,
                textModifier = Modifier
                    .fillMaxWidth())
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(end = 10.dp)
        ) {
            ImageComponent(imageModifier = Modifier.size(20.dp).padding(bottom = 2.dp), imageRes = statusDrawableRes, colorFilter = ColorFilter.tint(color = statusColor))
            AttachAppointmentStatus(statusText, statusColor)
        }
    }
}



@Composable
fun TherapistDashboardAppointmentWidget(appointment: Appointment,
                                        onArchiveAppointment: (Appointment) -> Unit,
                                        onUpdateToDone: (Appointment) -> Unit) {

    val appointmentStatus = appointment.bookingStatus
    var iconRes = "drawable/schedule.png"
    var statusText = "Pending"
    var statusColor: Color = Colors.primaryColor



    when (appointmentStatus) {
        BookingStatus.PENDING.toPath() -> {
            iconRes = "drawable/schedule.png"
            statusText = "Pending"
            statusColor = Colors.primaryColor
        }
        BookingStatus.POSTPONED.toPath() -> {
            iconRes = "drawable/appointment_postponed.png"
            statusText = "Postponed"
            statusColor = Colors.pinkColor
        }
        BookingStatus.DONE.toPath() -> {
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
            AttachTherapistAppointmentHeader(statusText, iconRes, statusColor, appointment, onUpdateToDone = {
                onUpdateToDone(it)
            }, onArchiveAppointment = {
                onArchiveAppointment(it)
            })
            AttachTherapistAppointmentContent(appointment)
        }
    }
}


@Composable
fun AttachTherapistAppointmentContent(appointment: Appointment) {

    TherapistAppointmentInfoWidget(appointment)
    CustomerInfoWidget(appointment)

}






