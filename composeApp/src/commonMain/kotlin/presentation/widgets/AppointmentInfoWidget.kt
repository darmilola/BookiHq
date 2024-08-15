package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.Models.Appointment
import domain.Enums.ServiceLocationEnum
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import presentations.components.TextComponent
import theme.styles.Colors

@Composable
fun AppointmentInfoWidget(appointment: Appointment) {

    val appointmentDateFormat = LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
        chars(", ")
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        dayOfMonth()
    }
    val platformTime = appointment.platformTime
    val isAm = if (platformTime?.isAm == true) "AM" else "PM"

    val appointmentDate =
        LocalDate(dayOfMonth = appointment.appointmentDay!!, monthNumber = appointment.appointmentMonth!!, year = appointment.appointmentYear!!).format(appointmentDateFormat)
    val appointmentTime = platformTime?.time+" "+isAm

    val columnModifier = Modifier
        .padding(start = 15.dp, end = 10.dp)
        .fillMaxWidth()
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = columnModifier
    ) {
        TextComponent(
            text = appointment.serviceTypeItem?.title.toString(),
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 5.dp)
        )


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
                            text = appointmentTime.toString() + " " + appointmentDate,
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
                TherapistDisplayItem(appointment.therapistInfo!!)
            }
        }
    }
}



@Composable
fun TherapistAppointmentInfoWidget(appointment: Appointment) {

    val appointmentDateFormat = LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
        chars(", ")
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        dayOfMonth()
    }
    val platformTime = appointment.platformTime
    val isAm = if (platformTime?.isAm == true) "AM" else "PM"

    val appointmentDate =
        LocalDate(dayOfMonth = appointment.appointmentDay!!, monthNumber = appointment.appointmentMonth!!, year = appointment.appointmentYear!!).format(appointmentDateFormat)
    val appointmentTime = platformTime?.time+" "+isAm

    val columnModifier = Modifier
        .padding(start = 15.dp, end = 10.dp)
        .fillMaxWidth()
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = columnModifier
    ) {
        TextComponent(
            text = appointment.serviceTypeItem?.title.toString(),
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 5.dp, top = 10.dp)
        )


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




@Composable
fun MeetingInfoWidget(appointment: Appointment) {

    val appointmentDateFormat = LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
        chars(", ")
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        dayOfMonth()
    }

    val appointmentDate =
        LocalDate(dayOfMonth = appointment.appointmentDay!!, monthNumber = appointment.appointmentMonth!!, year = appointment.appointmentYear!!).format(appointmentDateFormat)

    val platformTime = appointment.platformTime
    val isAm = if (platformTime?.isAm == true) "AM" else "PM"

    val appointmentTime = platformTime?.time+" "+isAm

    val columnModifier = Modifier
        .padding(start = 15.dp, end = 10.dp)
        .fillMaxWidth()
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = columnModifier
    ) {
        TextComponent(
            text = appointment.meetingDescription.toString(),
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Start,
            lineHeight = 20,
            maxLines = 3,
            fontWeight = FontWeight.Bold,
            textModifier = Modifier
                .fillMaxWidth().height(40.dp).padding(start = 5.dp)
        )

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
                            text = appointmentTime.toString() + " " + appointmentDate,
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
                }
            }
        }
    }
}