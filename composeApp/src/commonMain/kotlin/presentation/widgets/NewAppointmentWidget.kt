package presentation.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxHeight
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.background
import theme.styles.Colors
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import domain.Models.Appointment
import domain.Models.ServiceLocation
import domain.Models.ServiceStatus
import domain.Models.SpecialistInfo
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import presentation.appointments.AppointmentPresenter
import presentation.dialogs.PostponeDialog
import presentation.viewmodels.PostponementViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun NewAppointmentWidget(appointment: Appointment, appointmentPresenter: AppointmentPresenter? = null, postponementViewModel: PostponementViewModel? = null) {

    val appointmentStatus = appointment.serviceStatus
    val menuItems = arrayListOf<String>()

    var actionItem = ""
     actionItem = when (appointmentStatus) {
        ServiceStatus.Pending.toPath() -> {
            "Postpone"
        }

        else -> {
            "Delete"
        }
    }

    menuItems.add(actionItem)
    if (appointmentStatus == ServiceStatus.Done.toPath()){
        menuItems.add("Add Review")
    }


    var iconRes = "drawable/schedule.png"
    var statusText = "Pending"
    var statusColor: Color = Colors.primaryColor



    when (appointmentStatus) {
        ServiceStatus.Pending.toPath() -> {
            iconRes = "drawable/schedule.png"
            statusText = "Pending"
            statusColor = Colors.primaryColor
        }
        ServiceStatus.POSTPONED.toPath() -> {
            iconRes = "drawable/appointment_postponed.png"
            statusText = "Postponed"
            statusColor = Colors.pinkColor
        }
        ServiceStatus.Done.toPath() -> {
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
                AttachAppointmentHeader(statusText, iconRes, statusColor, appointment, menuItems, appointmentPresenter, postponementViewModel)
                AttachAppointmentContent(appointment)
            }
        }
}



@Composable
fun AttachAppointmentHeader(statusText: String, statusDrawableRes: String, statusColor: Color, appointment: Appointment, menuItems: ArrayList<String>, presenter: AppointmentPresenter? = null, postponementViewModel: PostponementViewModel? = null) {
    val expandedMenuItem = remember { mutableStateOf(false) }
    val openPostponeDialog = remember { mutableStateOf(false) }

    when {
        openPostponeDialog.value -> {
            if (presenter != null && postponementViewModel != null) {
                postponementViewModel.setCurrentAppointment(appointment)
                PostponeDialog(appointment,presenter, postponementViewModel, onDismissRequest = {
                    openPostponeDialog.value = false
                }, onConfirmation = {
                    openPostponeDialog.value = false
                })
            }
        }
    }

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
                text = appointment.services?.serviceTitle.toString(),
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
            modifier = Modifier.fillMaxWidth()
        ) {
            ImageComponent(imageModifier = Modifier.size(20.dp).padding(bottom = 2.dp), imageRes = statusDrawableRes, colorFilter = ColorFilter.tint(color = statusColor))
            AttachAppointmentStatus(statusText, statusColor)
            Row(modifier = Modifier
                .fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AttachIcon(
                    iconRes = "drawable/overflow_menu.png",
                    iconSize = 25,
                    iconTint = statusColor
                ) {
                    expandedMenuItem.value = true
                }
            }
            DropdownMenu(
                expanded = expandedMenuItem.value,
                onDismissRequest = { expandedMenuItem.value = false },
                modifier = Modifier
                    .fillMaxWidth(0.40f)
                    .background(Color.White)
            ) {
                menuItems.forEachIndexed { index, title ->
                    DropdownMenuItem(
                        onClick = {
                            if (title == "Postpone"){
                                openPostponeDialog.value = true
                            }
                            else if (title == "Delete"){
                                presenter?.deleteAppointment(appointment.appointmentId!!)
                            }
                        }) {
                        SubtitleTextWidget(text = title, fontSize = 16)
                    }
                }
            }
        }
    }
}

@Composable
fun AttachAppointmentStatus(status: String, statusColor: Color){
    val rowModifier = Modifier
        .padding(start = 5.dp)
        .wrapContentWidth()
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
        modifier = rowModifier
    ) {
        TextComponent(
            text = status,
            fontSize = 13,
            fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6,
            textColor = statusColor,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AttachAppointmentContent(appointment: Appointment) {

    val appointmentDateFormat =  LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
        chars(", ")
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        dayOfMonth()
    }
    val appointmentDate = LocalDate.parse(appointment.appointmentDate.toString()).format(appointmentDateFormat)
    val appointmentTime = appointment.serviceTime?.time

    val columnModifier = Modifier
        .padding(start = 15.dp, end = 10.dp)
        .fillMaxWidth()
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
        TextComponent(
            text = appointment.serviceTypeItem?.title.toString(),
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 5.dp))


        Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(top = 5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                val rowModifier = Modifier
                    .fillMaxWidth().height(25.dp)
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top,
                    modifier = rowModifier
                ) {
                    Box(modifier = Modifier.wrapContentWidth().fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                        TextComponent(
                            text = appointmentTime.toString()+" "+appointmentDate,
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
                    Box(modifier = Modifier.width(25.dp).fillMaxHeight().padding(start = 10.dp, end = 10.dp), contentAlignment = Alignment.Center) {
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(color = Colors.serviceLightGray)){}
                    }
                    Box(modifier = Modifier.wrapContentWidth().fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                        TextComponent(
                            text = if(appointment.serviceLocation == ServiceLocation.Spa.toPath()) "At The Spa" else "Home Service",
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
                TherapistDisplayItem(appointment.specialistInfo!!)
            }
        }

    }
}

@Composable
fun TherapistDisplayItem(specialistInfo: SpecialistInfo) {
    val profileInfo = specialistInfo.profileInfo
    val rowModifier = Modifier
        .fillMaxWidth().height(45.dp)

    Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart)
     {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {

            val iconModifier = Modifier
                .padding(top = 5.dp)
                .size(16.dp)

            val iconTextBoxModifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = iconTextBoxModifier
            ) {
                TherapistProfileImage(profileImageUrl = profileInfo?.profileImageUrl!!, isAsync = true)
                Box(modifier = Modifier.wrapContentWidth().fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                    TextComponent(
                        text = profileInfo.firstname+ " "+profileInfo.lastname,
                        fontSize = 15,
                        fontFamily = GGSansRegular,
                        textStyle = MaterialTheme.typography.h6,
                        textColor = Colors.serviceLightGray,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textModifier = Modifier.wrapContentSize().padding(start = 10.dp, end = 10.dp))
                }
                Row (horizontalArrangement = Arrangement.Center,
                    verticalAlignment  = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .width(60.dp)
                        .border(BorderStroke(1.dp, Colors.primaryColor), shape = RoundedCornerShape(5.dp))
                        .height(24.dp)) {
                    ImageComponent(imageModifier = Modifier.size(10.dp).padding(bottom = 2.dp), imageRes = "drawable/star_icon.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
                    TextComponent(
                        text = specialistInfo.rating.toString(),
                        fontSize = 12,
                        fontFamily = GGSansRegular,
                        textStyle = MaterialTheme.typography.h6,
                        textColor = Colors.primaryColor,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Medium,
                        textModifier = Modifier.padding(start = 3.dp))
                }

            }


        }
    }
}

@Composable
fun TherapistProfileImage(profileImageUrl: String, isAsync: Boolean = false) {
    Box(Modifier.wrapContentSize(), contentAlignment = Alignment.CenterStart) {
        Box(
            Modifier
                .size(30.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = Colors.primaryColor,
                    shape = CircleShape
                )
                .background(color = Color.Transparent)
        ) {
            val modifier = Modifier
                .padding(0.5.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = CircleShape
                )
                .fillMaxSize()
            ImageComponent(imageModifier = modifier, imageRes = profileImageUrl, isAsync = isAsync)
        }
    }
}




