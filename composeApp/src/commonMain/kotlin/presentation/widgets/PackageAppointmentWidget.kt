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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
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
import domain.Enums.AppointmentType
import domain.Enums.BookingStatus
import domain.Models.Appointment
import domain.Models.PlatformNavigator
import domain.Models.UserAppointment
import presentation.appointments.AppointmentPresenter
import presentation.dialogs.PostponeDialog
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.PostponementViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors


@Composable
fun PackageAppointmentWidget(userAppointment: UserAppointment? = null, appointmentPresenter: AppointmentPresenter? = null, postponementViewModel: PostponementViewModel? = null, mainViewModel: MainViewModel, availabilityPerformedActionUIStateViewModel: PerformedActionUIStateViewModel, onDeleteAppointment: (UserAppointment) -> Unit, onCancelAppointment: (UserAppointment) -> Unit, platformNavigator: PlatformNavigator,
                      onAddReview: (Appointment) -> Unit) {

    val appointment = userAppointment!!.resources
    val serviceAppointmentStatus = appointment?.bookingStatus
    val serviceMenuItems = arrayListOf<String>()

    var actionItem = ""
    actionItem = when (serviceAppointmentStatus) {
        BookingStatus.POSTPONED.toPath() -> {
            "Delete"
        }
        BookingStatus.BOOKING.toPath() -> {
            "Cancel Appointment"
        }
        else -> {
            "Postpone"
        }
    }

    serviceMenuItems.add(actionItem)
    if (serviceAppointmentStatus == BookingStatus.DONE.toPath()) {
        serviceMenuItems.clear()
        serviceMenuItems.add("Delete")
        serviceMenuItems.add("Add Review")
    }



    var serviceIconRes = "drawable/schedule.png"
    var serviceStatusText = "Pending"
    var serviceStatusColor: Color = Colors.primaryColor



    when (serviceAppointmentStatus) {
        BookingStatus.PENDING.toPath() -> {
            serviceIconRes = "drawable/schedule.png"
            serviceStatusText = "Pending"
            serviceStatusColor = Colors.primaryColor
        }
        BookingStatus.BOOKING.toPath() -> {
            serviceIconRes = "drawable/schedule.png"
            serviceStatusText = "Booking"
            serviceStatusColor = Colors.primaryColor
        }
        BookingStatus.POSTPONED.toPath() -> {
            serviceIconRes = "drawable/appointment_postponed.png"
            serviceStatusText = "Postponed"
            serviceStatusColor = Colors.pinkColor
        }
        BookingStatus.DONE.toPath() -> {
            serviceIconRes = "drawable/appointment_done.png"
            serviceStatusText = "Done"
            serviceStatusColor = Colors.greenColor
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
            AttachPackageAppointmentHeader(
                serviceStatusText,
                serviceIconRes,
                serviceStatusColor,
                userAppointment,
                serviceMenuItems,
                appointmentPresenter,
                postponementViewModel,
                availabilityPerformedActionUIStateViewModel,
                mainViewModel = mainViewModel,
                onDeleteAppointment = {
                    onDeleteAppointment(it)
                },
                onCancelAppointment = {
                    onCancelAppointment(it)
                },
                platformNavigator = platformNavigator, onAddReview = {
                    onAddReview(it)
                })
            AttachPackageAppointmentContent(appointment!!)
        }
    }
}

@Composable
fun AttachPackageAppointmentHeader(statusText: String, statusDrawableRes: String, statusColor: Color, userAppointment: UserAppointment, menuItems: ArrayList<String>, presenter: AppointmentPresenter? = null, postponementViewModel: PostponementViewModel? = null,
                                   availabilityPerformedActionUIStateViewModel: PerformedActionUIStateViewModel, mainViewModel: MainViewModel, onDeleteAppointment: (UserAppointment) -> Unit,onCancelAppointment: (UserAppointment) -> Unit, platformNavigator: PlatformNavigator, onAddReview: (Appointment) -> Unit) {
    val expandedMenuItem = remember { mutableStateOf(false) }
    val openPostponeDialog = remember { mutableStateOf(false) }

    when {
        openPostponeDialog.value -> {
            if (presenter != null && postponementViewModel != null) {
                postponementViewModel.setCurrentAppointment(userAppointment)
                PostponeDialog(userAppointment,presenter, postponementViewModel, mainViewModel = mainViewModel, availabilityPerformedActionUIStateViewModel, onDismissRequest = {
                    openPostponeDialog.value = false
                }, onConfirmation = {
                    openPostponeDialog.value = false
                }, platformNavigator = platformNavigator)
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
                text = userAppointment.resources!!.packageInfo!!.title,
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
                            if (title.contentEquals("Postpone", true)) {
                                openPostponeDialog.value = true
                            }
                            else if (title.contentEquals("Delete", true)) {
                                onDeleteAppointment(userAppointment)
                            }
                            else if (title.contentEquals("Cancel Appointment")){
                                onCancelAppointment(userAppointment)
                            }
                            else if (title.contentEquals("Add Review", true)) {
                                onAddReview(userAppointment.resources!!)
                            }
                        }) {
                        MultiLineTextWidget(text = title, fontSize = 16)
                    }
                }
            }
        }
    }
}

@Composable
fun PendingPackageAppointmentWidget(appointment: UserAppointment, onDeleteAppointment: (UserAppointment) -> Unit) {

    val serviceMenuItems = arrayListOf<String>()
    serviceMenuItems.add("Cancel Booking")

    val serviceIconRes = "drawable/schedule.png"
    val serviceStatusText = BookingStatus.BOOKING.toName()
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
fun RecentPackageAppointmentWidget(appointment: UserAppointment) {

    val serviceMenuItems = arrayListOf<String>()

    var serviceIconRes = "drawable/schedule.png"
    var serviceStatusText = BookingStatus.PENDING.toName()
    var serviceStatusColor: Color = Colors.primaryColor


    val serviceAppointmentStatus = appointment.resources?.bookingStatus
    var actionItem = ""
    actionItem = when (serviceAppointmentStatus) {
        BookingStatus.POSTPONED.toPath() -> {
            "Delete"
        }
        BookingStatus.BOOKING.toPath() -> {
            "Cancel Booking"
        }
        else -> {
            "Postpone"
        }
    }

    serviceMenuItems.add(actionItem)
    if (serviceAppointmentStatus == BookingStatus.DONE.toPath()) {
        serviceMenuItems.clear()
        serviceMenuItems.add("Delete")
        serviceMenuItems.add("Add Review")
    }

    when (serviceAppointmentStatus) {
        BookingStatus.PENDING.toPath() -> {
            serviceIconRes = "drawable/schedule.png"
            serviceStatusText = BookingStatus.PENDING.toName()
            serviceStatusColor = Colors.primaryColor
        }
        BookingStatus.BOOKING.toPath() -> {
            serviceIconRes = "drawable/schedule.png"
            serviceStatusText = BookingStatus.BOOKING.toName()
            serviceStatusColor = Colors.primaryColor
        }
        BookingStatus.POSTPONED.toPath() -> {
            serviceIconRes = "drawable/appointment_postponed.png"
            serviceStatusText = BookingStatus.POSTPONED.toName()
            serviceStatusColor = Colors.pinkColor
        }
        BookingStatus.DONE.toPath() -> {
            serviceIconRes = "drawable/appointment_done.png"
            serviceStatusText = BookingStatus.DONE.toName()
            serviceStatusColor = Colors.greenColor
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
            AttachRecentPackageAppointmentHeader(
                serviceStatusText,
                serviceIconRes,
                serviceStatusColor,
                appointment.resources!!)
            AttachPackageAppointmentContent(appointment.resources)
        }
    }
}

@Composable
fun AttachRecentPackageAppointmentHeader(statusText: String, statusDrawableRes: String, statusColor: Color, appointment: Appointment) {

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
                text = appointment.packageInfo!!.title,
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
    if (appointment.appointmentType ==  AppointmentType.PACKAGE.toPath()){
        PackageAppointmentInfoWidget(appointment)
    }
}


