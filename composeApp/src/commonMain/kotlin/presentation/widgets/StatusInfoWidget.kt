package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Enums.ServiceLocationEnum
import domain.Models.Appointment
import domain.Models.StatusInfoModel
import domain.Models.Vendor
import presentation.main.VendorLogo
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors
import utils.getDateTimeFromTimeStamp
import utils.getStatusDisplayTime

@Composable
fun StatusInfoWidget(vendorInfo: Vendor, currentTimeStamp: Long) {
    val statusDateTime = getDateTimeFromTimeStamp(currentTimeStamp)
    val displayTime = getStatusDisplayTime(statusDateTime)
    val columnModifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
    Row(
        modifier = columnModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(60.dp)
        ) {
            Box(Modifier.wrapContentSize(), contentAlignment = Alignment.CenterStart) {
                VendorLogo(imageUrl = vendorInfo.businessLogo!!, onVendorLogoClicked = {})
            }
        }

        Column(modifier = Modifier.fillMaxWidth().height(50.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center) {
            TextComponent(
                text = vendorInfo.businessName.toString(),
                fontSize = 16,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.White,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textModifier = Modifier.wrapContentSize())

            TextComponent(
                text = displayTime,
                fontSize = 15,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.Gray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textModifier = Modifier.wrapContentSize())

        }
      }
    }
