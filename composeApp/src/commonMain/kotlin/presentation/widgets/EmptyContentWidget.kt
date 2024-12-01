package presentation.widgets

import GGSansRegular
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
import domain.Models.getWidget
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors

@Composable
fun EmptyContentWidget(emptyText: String) {
    val columnModifier = Modifier
        .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
        .clickable {}
        .height(130.dp)
    Column(
        modifier = columnModifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {
        val modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
        Box(
            Modifier
                .clip(CircleShape)
                .size(80.dp)
                .background(color = Colors.lighterPrimaryColor),
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(imageModifier = Modifier
                .size(40.dp), imageRes = "drawable/empty_icon.png", colorFilter = ColorFilter.tint(color = Colors.darkPrimary))
        }
        TextComponent(
            text = emptyText,
            fontSize = 15,
            fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            lineHeight = 30,
            textModifier = modifier
        )
    }

}
