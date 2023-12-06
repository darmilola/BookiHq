package widgets

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansBold
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent

@Composable
fun ServicesWidget(iconRes: String, serviceTitle: String){
    val columnModifier = Modifier
        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 10.dp)
        .height(100.dp)
    MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment  = Alignment.CenterHorizontally,
        ) {
            val modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
            attachServiceImage(iconRes)
            TextComponent(
                text = serviceTitle,
                fontSize = 15,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                lineHeight = 30,
                textModifier = modifier
            )
        }
    }
}

@Composable
fun attachServiceImage(iconRes: String) {
    Box(
        Modifier
            .clip(CircleShape)
            .size(70.dp)
            .background(color = Color(color = 0x20ee7e91)),
        contentAlignment = Alignment.Center
    ) {
        val modifier = Modifier
            .size(30.dp)
        ImageComponent(imageModifier = modifier, imageRes = iconRes, colorFilter = ColorFilter.tint(color = Color(color = 0xFFF17C90)))
    }

}