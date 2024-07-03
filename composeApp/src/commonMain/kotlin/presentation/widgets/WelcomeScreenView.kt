package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun welcomeScreenView(displayText: String) {
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
            imageRes = "drawable/woman_welcome.jpg", contentScale = ContentScale.FillWidth)
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

