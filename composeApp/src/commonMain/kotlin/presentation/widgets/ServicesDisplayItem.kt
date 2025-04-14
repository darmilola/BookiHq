package presentation.widgets

import GGSansBold
import GGSansRegular
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.Models.Services
import domain.Models.getWidget
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun HomeServicesWidget(vendorService:Services, onServiceSelected: (Services) -> Unit){
    val columnModifier = Modifier
        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
        .clickable {
            onServiceSelected(vendorService)
        }
        .border(width = 1.dp, color = Colors.lightGray, shape = RoundedCornerShape(20.dp))
        .height(120.dp)
        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment  = Alignment.CenterHorizontally,
        ) {
            val modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
            AttachServiceImage(getWidget(vendorService.serviceInfo?.widgetCode!!))
            TextComponent(
                text = vendorService.serviceInfo.title,
                fontSize = 15,
                fontFamily = GGSansBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 30,
                textModifier = modifier
            )
        }
    }



@Composable
fun AttachServiceImage(iconRes: String, iconSize: Int = 50) {
    Box(
        Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        val modifier = Modifier
            .size(iconSize.dp)
        ImageComponent(imageModifier = modifier, imageRes = iconRes, colorFilter = ColorFilter.tint(color = Colors.iconTint))
    }

}