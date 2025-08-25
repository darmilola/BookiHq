package presentation.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxHeight
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.background
import theme.styles.Colors
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import domain.Enums.AppointmentType
import domain.Models.Appointment
import domain.Enums.BookingStatus
import domain.Models.PlatformNavigator
import domain.Models.TherapistInfo
import domain.Models.UserAppointment
import presentation.appointments.AppointmentPresenter
import presentation.dialogs.PostponeDialog
import presentation.viewmodels.PerformedActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PostponementViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun AppointmentWidget(userAppointment: UserAppointment? = null, appointmentPresenter: AppointmentPresenter? = null, postponementViewModel: PostponementViewModel? = null, mainViewModel: MainViewModel, availabilityPerformedActionUIStateViewModel: PerformedActionUIStateViewModel, onDeleteAppointment: (UserAppointment) -> Unit, onCancelAppointment: (UserAppointment) -> Unit, platformNavigator: PlatformNavigator,
                      onAddReview: (Appointment) -> Unit) {

    val appointment = userAppointment!!.resources
    val serviceAppointmentStatus = appointment?.bookingStatus
    val serviceMenuItems = arrayListOf<String>()

        var actionItem = ""
        actionItem = when (serviceAppointmentStatus) {
            BookingStatus.POSTPONED.toPath() -> {
                "Delete"
            }
            BookingStatus.CANCELLED.toPath() -> {
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
        BookingStatus.CANCELLED.toPath() -> {
            serviceIconRes = "drawable/schedule.png"
            serviceStatusText = "Cancelled"
            serviceStatusColor = theme.Colors.redColor
        }
        BookingStatus.POSTPONED.toPath() -> {
            serviceIconRes = "drawable/appointment_postponed.png"
            serviceStatusText = "Postponed"
            serviceStatusColor = theme.Colors.redColor
        }
        BookingStatus.DONE.toPath() -> {
            serviceIconRes = "drawable/appointment_done.png"
            serviceStatusText = "Done"
            serviceStatusColor = theme.Colors.greenColor
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
                    AttachServiceAppointmentHeader(
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
                    AttachServiceAppointmentContent(appointment!!)
                }
            }
}


@Composable
fun RecentAppointmentWidget(userAppointment: UserAppointment? = null) {

    val serviceAppointmentStatus = userAppointment?.resources?.bookingStatus
    val serviceMenuItems = arrayListOf<String>()

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



    var serviceIconRes = "drawable/schedule.png"
    var serviceStatusText = "Pending"
    var serviceStatusColor: Color = Colors.primaryColor



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
            serviceStatusColor = theme.Colors.redColor
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
            .background(color = Color.White)
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
            AttachRecentServiceAppointmentHeader(
                serviceStatusText,
                serviceIconRes,
                serviceStatusColor,
                userAppointment?.resources!!)
            AttachServiceAppointmentContent(userAppointment.resources)
        }
    }
}


@Composable
fun AttachRecentServiceAppointmentHeader(statusText: String, statusDrawableRes: String, statusColor: Color, appointment: Appointment) {

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 5.dp, top = 5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(0.70f).height(45.dp)
        ) {
            TextComponent(
                text = appointment.services?.serviceInfo?.title.toString(),
                fontSize = 16,
                textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
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
fun AttachServiceAppointmentHeader(statusText: String, statusDrawableRes: String, statusColor: Color, userAppointment: UserAppointment, menuItems: ArrayList<String>, presenter: AppointmentPresenter? = null, postponementViewModel: PostponementViewModel? = null,
                                   availabilityPerformedActionUIStateViewModel: PerformedActionUIStateViewModel, mainViewModel: MainViewModel, onDeleteAppointment: (UserAppointment) -> Unit, onCancelAppointment: (UserAppointment) -> Unit, platformNavigator: PlatformNavigator, onAddReview: (Appointment) -> Unit) {
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
                text = userAppointment.resources?.services?.serviceInfo?.title.toString(),
                fontSize = 16,
                textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
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
                       iconTint = theme.Colors.darkPrimary
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
                               else if (title.contentEquals("Cancel Booking")){
                                   onCancelAppointment(userAppointment)
                               }
                               else if (title.contentEquals("Add Review", true)) {
                                   onAddReview(userAppointment.resources!!)
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
            textStyle = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            textColor = statusColor,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun AttachServiceAppointmentContent(appointment: Appointment) {
    if (appointment.appointmentType ==  AppointmentType.SINGLE.toPath()){
        ServiceAppointmentInfoWidget(appointment)
    }
}



@Composable
fun TherapistDisplayItem(therapistInfo: TherapistInfo?) {
    var profileName = ""
    var imageUrl = ""
    if (therapistInfo == null){
        profileName = "No Therapist"
        imageUrl = "drawable/facials_icon.png"
    }
    else{
        val profileInfo = therapistInfo.profileInfo
        profileName = profileInfo!!.firstname+ " "+profileInfo.lastname
        imageUrl = profileInfo.profileImageUrl!!
    }
    val rowModifier = Modifier
        .fillMaxWidth().height(40.dp)

    Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart)
     {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {

            val iconTextBoxModifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = iconTextBoxModifier
            ) {
                TherapistProfileImage(profileImageUrl = imageUrl, isAsync = therapistInfo != null)
                Box(modifier = Modifier.wrapContentWidth().fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                    TextComponent(
                        text = profileName,
                        fontSize = 15,
                        textStyle = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                        textColor = theme.Colors.grayColor,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textModifier = Modifier.wrapContentSize().padding(start = 10.dp, end = 10.dp))
                }

            }

        }
    }
}

@Composable
fun AttachIcon(iconRes: String = "drawable/location_icon_filled.png", iconSize: Int = 16, iconTint: Color = Colors.primaryColor, onIconClicked:() -> Unit) {
    val modifier = Modifier
        .padding(top = 2.dp)
        .clickable {
            onIconClicked()
        }
        .size(iconSize.dp)
    ImageComponent(imageModifier = modifier, imageRes = iconRes, colorFilter = ColorFilter.tint(color = iconTint))
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




