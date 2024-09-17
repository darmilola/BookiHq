package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import domain.Enums.ServiceLocationEnum
import domain.Models.Appointment
import domain.Models.UserAppointment
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors

@Composable
fun PendingPackageAppointmentWidget(appointment: UserAppointment, onDeleteAppointment: (UserAppointment) -> Unit) {

    val serviceMenuItems = arrayListOf<String>()
    serviceMenuItems.add("Cancel Booking")

    val serviceIconRes = "drawable/schedule.png"
    val serviceStatusText = "Pending"
    val serviceStatusColor: Color = Colors.primaryColor

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
            AttachPendingPackageAppointmentHeader(
                serviceStatusText,
                serviceIconRes,
                serviceStatusColor,
                appointment,
                serviceMenuItems,
                onDeleteAppointment = {
                    onDeleteAppointment(it)
                })
            AttachPackageAppointmentContent(appointment.resources!!)
        }
    }
}


@Composable
fun AttachPendingPackageAppointmentHeader(statusText: String, statusDrawableRes: String, statusColor: Color, appointment: UserAppointment, menuItems: ArrayList<String>,
                                   onDeleteAppointment: (UserAppointment) -> Unit) {
    val expandedMenuItem = remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 5.dp, top = 15.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(0.70f).height(60.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth(0.08f).fillMaxHeight(), contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(color = statusColor)){}
            }
            TextComponent(
                text = appointment.resources!!.packageInfo!!.title,
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
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AttachIcon(
                    iconRes = "drawable/overflow_menu.png",
                    iconSize = 20,
                    iconTint = statusColor
                ) {
                    expandedMenuItem.value = true
                }
                DropdownMenu(
                    expanded = expandedMenuItem.value,
                    onDismissRequest = { expandedMenuItem.value = false },
                    modifier = Modifier
                        .fillMaxWidth(0.50f)
                        .background(Color.White)
                ) {
                    menuItems.forEachIndexed { index, title ->
                        DropdownMenuItem(
                            onClick = {
                                if (title.contentEquals("Cancel Booking", true)) {
                                    onDeleteAppointment(appointment)
                                }
                            }) {
                            MultiLineTextWidget(text = title, fontSize = 16)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AttachPackageAppointmentContent(appointment: Appointment) {
    val appointmentDateFormat = LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
        chars(", ")
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        dayOfMonth()
    }
    val platformTime = appointment.platformTime
    val isAm = if (platformTime.isAm) "AM" else "PM"

    val appointmentDate =
        LocalDate(dayOfMonth = appointment.appointmentDay!!, monthNumber = appointment.appointmentMonth!!, year = appointment.appointmentYear!!).format(appointmentDateFormat)
    val appointmentTime = platformTime.time +" "+isAm

    val columnModifier = Modifier
        .padding(start = 15.dp, end = 10.dp)
        .fillMaxWidth()
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = columnModifier
    ) {
        PackageInfo(appointment.packageInfo!!)
        Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(top = 5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val rowModifier = Modifier
                    .fillMaxWidth().height(25.dp)
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top,
                    modifier = rowModifier
                ) {
                    Box(
                        modifier = Modifier.wrapContentWidth().fillMaxHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        TextComponent(
                            text = "$appointmentTime $appointmentDate",
                            textModifier = Modifier.wrapContentSize()
                                .padding(start = 5.dp),
                            fontSize = 15,
                            fontFamily = GGSansRegular,
                            textStyle = MaterialTheme.typography.h6,
                            textColor = Colors.serviceLightGray,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    if (appointment.serviceLocation == ServiceLocationEnum.MOBILE.toPath()) {
                        Box(
                            modifier = Modifier.width(25.dp).fillMaxHeight()
                                .padding(start = 10.dp, end = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier.size(6.dp).clip(CircleShape)
                                    .background(color = Colors.serviceLightGray)
                            ) {}
                        }
                        Box(
                            modifier = Modifier.wrapContentWidth().fillMaxHeight(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            TextComponent(
                                text = "Mobile Service",
                                textModifier = Modifier.wrapContentSize(),
                                fontSize = 14,
                                fontFamily = GGSansRegular,
                                textStyle = MaterialTheme.typography.h6,
                                textColor = Colors.serviceLightGray,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    }
}


