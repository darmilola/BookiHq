package presentation.widgets

import GGSansBold
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors

@Composable
fun welcomeScreenView(displayText: String, imageRes: String) {
    val bgStyle = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color(color = 0x60000000),
                    Color(color = 0x50000000),
                    Color.Black)
            )
        )
    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
        ImageComponent(imageModifier = Modifier.fillMaxHeight().fillMaxWidth(),
            imageRes = imageRes, contentScale = ContentScale.FillBounds)
        Box(modifier = bgStyle)
        AttachTextContent(displayText = displayText)
    }
}

@Composable
fun AttachTextContent(displayText: String){
    Box(modifier = Modifier.fillMaxWidth(0.70f).fillMaxHeight(0.90f).padding(start = 30.dp), contentAlignment = Alignment.BottomStart) {
        TextComponent(
            text = displayText,
            fontSize = 23,
            fontFamily = GGSansRegular,
            textStyle = TextStyle(),
            textColor = Color.White,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 30
        )
    }
}


@Composable
fun onBoardingScreenView(subtitleText: String, titleText: String, imageRes: String) {
    Column(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.50f), contentAlignment = Alignment.Center){
            ImageComponent(
                imageModifier = Modifier.size(250.dp),
                imageRes = imageRes)
        }

        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.30f), contentAlignment = Alignment.TopCenter){
            TextComponent(
                text = titleText,
                fontSize = 28,
                textStyle = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 40,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textModifier = Modifier.fillMaxWidth(0.80f)
            )
        }

        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.TopCenter){
            TextComponent(
                text = subtitleText,
                fontSize = 18,
                textStyle = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.fillMaxWidth(0.80f)
            )
        }
    }
}



