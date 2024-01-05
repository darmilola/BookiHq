package widgets

import GGSansRegular
import GGSansSemiBold
import Styles.Colors
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent

@Composable
fun AppointmentWidget(iconRes: String = "drawable/schedule.png", iconSize: Int = 35, statusText: String = "PENDING", statusColor: Color = Colors.primaryColor, statusBgColor: Color = Colors.lightPrimaryColor) {
    val boxModifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp)
        .background(color = Color.White)
        .height(230.dp)

    val innerContainerModifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.75f)
        .padding(bottom = 20.dp)
        .background(color = statusBgColor, shape = RoundedCornerShape(10.dp))

    val cardContainerModifier = Modifier
        .padding(top = 65.dp)
        .fillMaxWidth(0.92f)
        .fillMaxHeight(0.83f)

    Box(contentAlignment = Alignment.TopCenter, modifier = boxModifier) {
        Row(modifier = innerContainerModifier,
             horizontalArrangement = Arrangement.Center,
             verticalAlignment  = Alignment.Top) {
            Row(
                modifier = Modifier.fillMaxWidth(0.92f).height(60.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth(0.80f).fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                    TextComponent(
                        text = "Haircut is the Service Name",
                        fontSize = 21,
                        fontFamily = GGSansSemiBold,
                        textStyle = TextStyle(),
                        textColor = statusColor,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 30,
                        textModifier = Modifier,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(modifier = Modifier.fillMaxWidth()) {


                    //overflow

                }
            }

        }
        Card(modifier = cardContainerModifier,
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)) {


            Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                Column(modifier = Modifier.fillMaxWidth(0.70f).fillMaxHeight(),
                     verticalArrangement = Arrangement.Center,
                     horizontalAlignment = Alignment.CenterHorizontally) {

                    val rowModifier = Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth().wrapContentHeight()
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top,
                        modifier = rowModifier
                    ) {
                        Box(modifier = Modifier.fillMaxWidth(0.15f), contentAlignment = Alignment.Center) {
                            AttachIcon(iconRes = "drawable/time_outline.png", iconSize = 20)
                        }
                        TextComponent(
                            text = "5 PM  23  June  2024",
                            fontSize = 16,
                            fontFamily = GGSansRegular,
                            textStyle = TextStyle(),
                            textColor = Color.Gray,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold,
                            textModifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top,
                        modifier =  Modifier
                            .padding(start = 10.dp, top = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.fillMaxWidth(0.15f), contentAlignment = Alignment.Center) {
                            AttachIcon(iconSize = 18)
                        }
                        TextComponent(
                            text = "Savanna Beauty Services",
                            fontSize = 16,
                            fontFamily = GGSansRegular,
                            textStyle = TextStyle(),
                            textColor = Color.Gray,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold,
                            textModifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.fillMaxWidth(0.15f), contentAlignment = Alignment.Center) {
                            AttachIcon(iconRes = "drawable/therapist_3.png", iconSize = 24)
                        }

                        TextComponent(
                            text = "Helena McCain",
                            fontSize = 16,
                            fontFamily = GGSansRegular,
                            textStyle = TextStyle(),
                            textColor = Color.Gray,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold,
                            textModifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }
                Box(Modifier.fillMaxWidth().fillMaxHeight().background(color = statusColor)) {

                    AttachAppointmentStatus(iconRes = iconRes, iconSize = iconSize, statusText = statusText)

                }
            }

        }

    }

}

@Composable
fun AttachIcon(iconRes: String = "location_icon_filled.png", iconSize: Int = 16) {
    val modifier = Modifier
        .padding(top = 2.dp)
        .size(iconSize.dp)
    ImageComponent(imageModifier = modifier, imageRes = iconRes, colorFilter = ColorFilter.tint(color = Color.LightGray))
}

@Composable
fun AttachAppointmentStatus(iconRes: String = "drawable/schedule.png", iconSize: Int = 35, statusText: String = "PENDING") {
    val modifier = Modifier
        .padding(top = 2.dp)
        .size(iconSize.dp)

     Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(),
             verticalArrangement = Arrangement.Center,
             horizontalAlignment = Alignment.CenterHorizontally) {

         ImageComponent(imageModifier = modifier, imageRes = iconRes, colorFilter = ColorFilter.tint(color = Color.White))

         TextComponent(
             text = statusText,
             fontSize = 15,
             fontFamily = GGSansRegular,
             textStyle = TextStyle(),
             textColor = Color.White,
             textAlign = TextAlign.Center,
             fontWeight = FontWeight.Bold,
             lineHeight = 30,
             textModifier = Modifier.fillMaxWidth().padding(top = 15.dp),
             maxLines = 1,
             overflow = TextOverflow.Ellipsis
         )

     }


}
