package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentations.components.TextComponent

@Composable
fun SubtitleTextWidget(text: String, fontSize: Int = 16, textAlign: TextAlign = TextAlign.Left, textColor: Color = Color.DarkGray) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        TextComponent(
            textModifier = Modifier.fillMaxWidth().wrapContentHeight(), text = text, fontSize = fontSize, fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6, textColor = textColor, textAlign = textAlign,
            fontWeight = FontWeight.Medium, lineHeight = 30, maxLines = 3,  overflow = TextOverflow.Ellipsis)
    }

}