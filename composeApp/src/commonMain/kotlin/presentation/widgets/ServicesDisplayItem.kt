package presentation.widgets

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentation.components.ImageComponent
import presentation.viewmodels.MainViewModel
import presentations.components.TextComponent

@Composable
fun HomeServicesWidget(iconRes: String, serviceTitle: String, mainViewModel: MainViewModel, iconSize: Int = 40){
    val columnModifier = Modifier
        .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
        .clickable {
            mainViewModel.setId(1)

        }
        .height(130.dp)
        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment  = Alignment.CenterHorizontally,
        ) {
            val modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
            AttachServiceImage(iconRes)
            TextComponent(
                text = serviceTitle,
                fontSize = 18,
                fontFamily = GGSansSemiBold,
                textStyle = TextStyle(),
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                lineHeight = 30,
                textModifier = modifier
            )
        }
    }



@Composable
fun AttachServiceImage(iconRes: String, iconSize: Int = 40) {
    Box(
        Modifier
            .clip(CircleShape)
            .fillMaxWidth()
            .height(90.dp)
            .background(color = Colors.lighterPrimaryColor),
        contentAlignment = Alignment.Center
    ) {
        val modifier = Modifier
            .size(iconSize.dp)
        ImageComponent(imageModifier = modifier, imageRes = iconRes, colorFilter = ColorFilter.tint(color = Colors.darkPrimary))
    }

}