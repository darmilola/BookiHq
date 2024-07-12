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
import domain.Enums.AppointmentType
import domain.Enums.MeetingStatus
import domain.Enums.ServiceStatusEnum
import domain.Models.TherapistInfo
import presentation.appointments.AppointmentPresenter
import presentation.dialogs.PostponeDialog
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.PostponementViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun AppointmentWidget(appointment: Appointment, appointmentPresenter: AppointmentPresenter? = null, postponementViewModel: PostponementViewModel? = null, mainViewModel: MainViewModel, availabilityActionUIStateViewModel: ActionUIStateViewModel, isFromHomeTab: Boolean,onDeleteAppointment: (Appointment) -> Unit) {

    val serviceAppointmentStatus = appointment.serviceStatus
    val serviceMenuItems = arrayListOf<String>()

        var actionItem = ""
        actionItem = when (serviceAppointmentStatus) {
            ServiceStatusEnum.POSTPONED.toPath() -> {
                "Delete"
            }

            else -> {
                "Postpone"
            }
        }

        serviceMenuItems.add(actionItem)
        if (serviceAppointmentStatus == ServiceStatusEnum.DONE.toPath()) {
            serviceMenuItems.add("Add Review")
        }



    var serviceIconRes = "drawable/schedule.png"
    var serviceStatusText = "Pending"
    var serviceStatusColor: Color = Colors.primaryColor



    when (serviceAppointmentStatus) {
        ServiceStatusEnum.PENDING.toPath() -> {
            serviceIconRes = "drawable/schedule.png"
            serviceStatusText = "Pending"
            serviceStatusColor = Colors.primaryColor
        }
        ServiceStatusEnum.POSTPONED.toPath() -> {
            serviceIconRes = "drawable/appointment_postponed.png"
            serviceStatusText = "Postponed"
            serviceStatusColor = Colors.pinkColor
        }
        ServiceStatusEnum.DONE.toPath() -> {
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
                    AttachServiceAppointmentHeader(
                        serviceStatusText,
                        serviceIconRes,
                        serviceStatusColor,
                        appointment,
                        serviceMenuItems,
                        appointmentPresenter,
                        postponementViewModel,
                        availabilityActionUIStateViewModel,
                        mainViewModel = mainViewModel,
                        isFromHomeTab, onDeleteAppointment = {
                            onDeleteAppointment(it)
                        })
                    AttachAppointmentContent(appointment)
                }
            }
}


@Composable
fun MeetingAppointmentWidget(appointment: Appointment, appointmentPresenter: AppointmentPresenter? = null, isFromHomeTab: Boolean) {
    val meetingAppointmentStatus = appointment.serviceStatus
    val meetingMenuItems = arrayListOf<String>()

    if (meetingAppointmentStatus == ServiceStatusEnum.PENDING.toPath()) {
            meetingMenuItems.add("Join Meeting")
        } else {
            meetingMenuItems.add("Delete")
      }


    var meetingIconRes = "drawable/schedule.png"
    var meetingStatusText = "Pending"
    var meetingStatusColor: Color = Colors.primaryColor


    when (meetingAppointmentStatus) {
        MeetingStatus.Pending.toPath() -> {
            meetingIconRes = "drawable/schedule.png"
            meetingStatusText = "Pending"
            meetingStatusColor = Colors.primaryColor
        }
        MeetingStatus.Done.toPath() -> {
            meetingIconRes = "drawable/appointment_done.png"
            meetingStatusText = "Done"
            meetingStatusColor = Colors.greenColor
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
                AttachMeetingAppointmentHeader(meetingStatusText,
                    meetingIconRes,
                    meetingStatusColor,
                    appointment,
                    meetingMenuItems,
                    appointmentPresenter,
                    isFromHomeTab)
                AttachAppointmentContent(appointment)

            }
    }
}



@Composable
fun AttachServiceAppointmentHeader(statusText: String, statusDrawableRes: String, statusColor: Color, appointment: Appointment, menuItems: ArrayList<String>, presenter: AppointmentPresenter? = null, postponementViewModel: PostponementViewModel? = null,
                                   availabilityActionUIStateViewModel: ActionUIStateViewModel, mainViewModel: MainViewModel, isFromHomeTab: Boolean = false, onDeleteAppointment: (Appointment) -> Unit) {
    val expandedMenuItem = remember { mutableStateOf(false) }
    val openPostponeDialog = remember { mutableStateOf(false) }

    when {
        openPostponeDialog.value -> {
            if (presenter != null && postponementViewModel != null) {
                postponementViewModel.setCurrentAppointment(appointment)
                PostponeDialog(appointment,presenter, postponementViewModel, mainViewModel = mainViewModel, availabilityActionUIStateViewModel, onDismissRequest = {
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

           if (!isFromHomeTab) {
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
                               } else if (title.contentEquals("Delete", true)) {
                                   onDeleteAppointment(appointment)
                               }
                           }) {
                           SubtitleTextWidget(text = title, fontSize = 16)
                       }
                   }
               }
           }
        }
    }
}


@Composable
fun AttachMeetingAppointmentHeader(statusText: String, statusDrawableRes: String, statusColor: Color, appointment: Appointment, menuItems: ArrayList<String>, presenter: AppointmentPresenter? = null, isFromHomeTab: Boolean) {
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
                text = "Meeting With Therapist",
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
          if (!isFromHomeTab) {
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
                              println("Title is $title")
                              if (title.contentEquals("Join Meeting", ignoreCase = true)) {
                                  presenter?.joinMeeting(
                                      customParticipantId = appointment.userId.toString(),
                                      presetName = "group_call_participant",
                                      meetingId = appointment.meetingId!!
                                  )
                              } else if (title.contentEquals("Delete", true)) {
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
    if (appointment.appointmentType == AppointmentType.SERVICE.toPath()) {
        AppointmentInfoWidget(appointment)
    }
    else{
        MeetingInfoWidget(appointment)
    }
}



@Composable
fun TherapistDisplayItem(therapistInfo: TherapistInfo) {
    val profileInfo = therapistInfo.profileInfo
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




