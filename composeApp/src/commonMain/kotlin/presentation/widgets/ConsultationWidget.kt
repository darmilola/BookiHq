package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
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
import domain.Models.Auth0ConnectionType
import domain.Models.ServiceLocation
import domain.Models.ServiceStatus
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import presentation.appointments.AppointmentPresenter
import presentation.components.ButtonComponent
import presentation.components.IconButtonComponent
import presentation.components.RightIconButtonComponent
import presentation.viewmodels.PostponementViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors

@Composable
fun ConsultationWidget(onCreateSessionClick:() -> Unit) {

    val boxBgModifier =
        Modifier
            .padding(bottom = 5.dp, top = 5.dp, start = 10.dp)
            .height(220.dp)
            .width(350.dp)
            .border(border = BorderStroke(1.4.dp, Colors.lightGray), shape = RoundedCornerShape(20.dp))

    Box(modifier = boxBgModifier) {
        val columnModifier = Modifier
            .padding(start = 5.dp, bottom = 10.dp)
            .fillMaxWidth()
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = columnModifier
        ) {

            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.60f)) {
                 ConsultationItemTopBar()
            }
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.Center) {

                val buttonStyle = Modifier
                    .padding(bottom = 10.dp, top = 10.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .clickable {
                        onCreateSessionClick()
                    }
                    .height(50.dp)

                   RightIconButtonComponent(modifier = buttonStyle, buttonText = "Book Consultation", borderStroke = BorderStroke(0.8.dp, Colors.darkPrimary), colors = ButtonDefaults.buttonColors(backgroundColor = Colors.lightPrimaryColor), fontSize = 16, shape = CircleShape, textColor = Colors.darkPrimary, style = MaterialTheme.typography.h4, iconRes = "drawable/forward_arrow.png",  colorFilter = ColorFilter.tint(color = Colors.darkPrimary)){}
            }
        }
    }
}

@Composable
fun ConsultationItemTopBar() {


    Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(start = 15.dp, end = 10.dp, top = 20.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().height(40.dp)
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth(0.08f).fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier.size(6.dp).clip(CircleShape)
                            .background(color = Colors.primaryColor)
                    ) {}
                }
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    TextComponent(
                        text = "Family Therapy",
                        fontSize = 25,
                        fontFamily = GGSansSemiBold,
                        textStyle = TextStyle(),
                        textColor = Colors.darkPrimary,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 30,
                        textModifier = Modifier.fillMaxWidth().wrapContentHeight()
                    )
                }
            }


            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().height(40.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    TextComponent(
                        text = "This is a session for the whole family",
                        fontSize = 16,
                        fontFamily = GGSansSemiBold,
                        textStyle = TextStyle(),
                        textColor = Colors.darkPrimary,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 30,
                        textModifier = Modifier.fillMaxWidth().wrapContentHeight()
                    )
                }
            }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier
                            .fillMaxWidth().height(25.dp)
                    ) {
                        Box(
                            modifier = Modifier.wrapContentWidth().fillMaxHeight(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            TextComponent(
                                text = "3 hrs - 3 Family Members",
                                textModifier = Modifier.wrapContentSize()
                                    .padding(start = 5.dp),
                                fontSize = 15,
                                fontFamily = GGSansRegular,
                                textStyle = MaterialTheme.typography.h6,
                                textColor = Color.LightGray,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Box(
                            modifier = Modifier.width(25.dp).fillMaxHeight()
                                .padding(start = 5.dp, end = 5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier.size(6.dp).clip(CircleShape)
                                    .background(color = Color.LightGray)) {}
                        }
                        Box(
                            modifier = Modifier.wrapContentWidth().fillMaxHeight(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            TextComponent(
                                text = "Virtual",
                                textModifier = Modifier.wrapContentSize(),
                                fontSize = 14,
                                fontFamily = GGSansRegular,
                                textStyle = MaterialTheme.typography.h6,
                                textColor = Color.LightGray,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Bold,
                            )
                        }
            }
        }

    }
}



