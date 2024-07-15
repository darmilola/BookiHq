package presentation.widgets

import GGSansRegular
import GGSansSemiBold
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
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
import domain.Enums.ServiceLocationEnum
import domain.Models.Appointment
import domain.Models.Vendor
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors


@Composable
fun CustomerInfoWidget(appointment: Appointment) {
    val customerInfo = appointment.customerInfo
    val columnModifier = Modifier
        .padding(start = 15.dp, end = 10.dp)
        .fillMaxWidth()
         Column(
                modifier = columnModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {

             Row(
                 horizontalArrangement = Arrangement.Start,
                 verticalAlignment = Alignment.CenterVertically,
                 modifier = Modifier
                     .fillMaxWidth().height(35.dp)
             ) {

                 Box(Modifier.wrapContentSize(), contentAlignment = Alignment.CenterStart) {

                     ImageComponent(
                         imageModifier = Modifier.size(25.dp),
                         imageRes = "drawable/user_outline.png",
                         ColorFilter.tint(color = Color.Gray)
                     )
                 }

                 TextComponent(
                     text = customerInfo!!.firstname + " " + customerInfo.lastname,
                     fontSize = 15,
                     fontFamily = GGSansRegular,
                     textStyle = MaterialTheme.typography.h6,
                     textColor = Color.Gray,
                     textAlign = TextAlign.Left,
                     fontWeight = FontWeight.Bold,
                     maxLines = 2,
                     overflow = TextOverflow.Ellipsis,
                     textModifier = Modifier.wrapContentSize()
                         .padding(start = 10.dp, end = 10.dp)
                 )
             }

             if (customerInfo!!.contactPhone != null) {

                 Row(
                     horizontalArrangement = Arrangement.Start,
                     verticalAlignment = Alignment.CenterVertically,
                     modifier = Modifier
                         .fillMaxWidth().height(35.dp)
                 ) {

                     Box(Modifier.wrapContentSize(), contentAlignment = Alignment.CenterStart) {

                         ImageComponent(
                             imageModifier = Modifier.size(25.dp),
                             imageRes = "drawable/phone_icon.png",
                             ColorFilter.tint(color = Color.Gray)
                         )
                     }

                     TextComponent(
                         text = customerInfo!!.contactPhone.toString(),
                         fontSize = 15,
                         fontFamily = GGSansRegular,
                         textStyle = MaterialTheme.typography.h6,
                         textColor = Color.Gray,
                         textAlign = TextAlign.Left,
                         fontWeight = FontWeight.Bold,
                         maxLines = 2,
                         overflow = TextOverflow.Ellipsis,
                         textModifier = Modifier.wrapContentSize()
                             .padding(start = 10.dp, end = 10.dp)
                     )
                 }
             }

             if (appointment.serviceLocation == ServiceLocationEnum.MOBILE.toPath()) {

                 Row(
                     horizontalArrangement = Arrangement.Start,
                     verticalAlignment = Alignment.CenterVertically,
                     modifier = Modifier
                         .fillMaxWidth().height(40.dp)
                 ) {

                     Box(Modifier.wrapContentSize(), contentAlignment = Alignment.CenterStart) {

                         ImageComponent(
                             imageModifier = Modifier.size(25.dp),
                             imageRes = "drawable/location_icon.png",
                             ColorFilter.tint(color = Color.Gray)
                         )
                     }

                     TextComponent(
                         text = customerInfo?.address.toString(),
                         fontSize = 15,
                         fontFamily = GGSansRegular,
                         textStyle = MaterialTheme.typography.h6,
                         textColor = Color.Gray,
                         textAlign = TextAlign.Left,
                         fontWeight = FontWeight.Bold,
                         maxLines = 2,
                         lineHeight = 20,
                         overflow = TextOverflow.Ellipsis,
                         textModifier = Modifier.wrapContentSize()
                             .padding(start = 10.dp, end = 10.dp)
                     )
                 }


             }
         }

}

@Composable
fun VendorDisplayItem(vendor: Vendor) {
    val businessLogo = vendor.businessLogo
    val rowModifier = Modifier
        .fillMaxWidth().height(35.dp)

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
                VendorImage(profileImageUrl = businessLogo!!, isAsync = true)
                Box(modifier = Modifier.wrapContentWidth().fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                    TextComponent(
                        text = vendor.businessName!!,
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
fun VendorImage(profileImageUrl: String, isAsync: Boolean = false) {
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




