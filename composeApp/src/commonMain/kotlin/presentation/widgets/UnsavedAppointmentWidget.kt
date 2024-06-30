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
import domain.Models.ServiceTypeTherapists
import domain.Models.UnsavedAppointment
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun UnsavedAppointmentWidget(unsavedAppointment: UnsavedAppointment? = null, onRemoveItem: (UnsavedAppointment) -> Unit) {

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
            UnsavedAppointmentHeader(unsavedAppointment?.services?.serviceTitle!!, unsavedAppointment.serviceTypeItem?.price!!, onRemoveItem = {
                onRemoveItem(unsavedAppointment)
            })
            UnsavedAppointmentContent(unsavedAppointment)
        }
    }
}



@Composable
fun UnsavedAppointmentHeader(serviceTitle: String, amount: Int, onRemoveItem: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, top = 15.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(0.60f).height(60.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth(0.08f).fillMaxHeight(), contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(color = Colors.pinkColor)){}
            }
            TextComponent(
                text = serviceTitle,
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
            modifier = Modifier.fillMaxWidth(0.85f).padding(end = 10.dp)
        ) {
            ImageComponent(imageModifier = Modifier.size(20.dp).padding(bottom = 2.dp), imageRes = "drawable/dollar_icon.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
            TextComponent(
                text = amount.toString(),
                fontSize = 16,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.primaryColor,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                textModifier = Modifier
                    .wrapContentWidth().padding(start = 2.dp, end = 10.dp))
        }
        Row(modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {

            val expandedMenuItem = remember { mutableStateOf(false) }
            presentation.main.AttachIcon(
                iconRes = "drawable/overflow_menu.png",
                iconSize = 25,
                iconTint = Colors.primaryColor
            ) {
                expandedMenuItem.value = true
            }

            val menuItems = arrayListOf<String>()
            menuItems.add("Delete")

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
                            onRemoveItem()
                        }) {
                        SubtitleTextWidget(text = title, fontSize = 20)
                    }
                }
            }

        }
    }
}

@Composable
fun UnsavedAppointmentContent(unsavedAppointment: UnsavedAppointment?) {

  val appointmentDateFormat =  LocalDate.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
        chars(", ")
        monthName(MonthNames.ENGLISH_FULL)
        char(' ')
        dayOfMonth()
    }

    val unsavedAppointmentDay = unsavedAppointment?.day
    val unsavedAppointmentMonth = unsavedAppointment?.month
    val unsavedAppointmentYear = unsavedAppointment?.year

   val appointmentDate = LocalDate(dayOfMonth = unsavedAppointmentDay!!, monthNumber = unsavedAppointmentMonth!!, year = unsavedAppointmentYear!!).format(appointmentDateFormat)

    val columnModifier = Modifier
        .padding(start = 15.dp, end = 10.dp)
        .fillMaxWidth()
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
        TextComponent(
            text = unsavedAppointment.serviceTypeItem?.title!!,
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
                            text = appointmentDate.toString(),
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
                            text = if (unsavedAppointment.isHomeService) "Home Service" else "At The Spa",
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
                UnsavedTherapistDisplayItem(unsavedAppointment.serviceTypeTherapists!!)
            }
        }

    }
}

@Composable
fun UnsavedTherapistDisplayItem(serviceTypeTherapists: ServiceTypeTherapists) {
    val profileInfo = serviceTypeTherapists.therapistInfo?.profileInfo
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
                UnsavedTherapistProfileImage(profileImageUrl = profileInfo?.profileImageUrl!!, isAsync = true)
                Box(modifier = Modifier.wrapContentWidth().fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                    TextComponent(
                        text = profileInfo.firstname!!+" "+profileInfo.lastname,
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
fun UnsavedTherapistProfileImage(profileImageUrl: String, isAsync: Boolean = false) {
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




