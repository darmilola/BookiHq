package presentation.widgets

import GGSansRegular
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentation.components.TextComponent

@Composable
fun SubtitleTextWidget(text: String, fontSize: Int = 18, textAlign: TextAlign = TextAlign.Left, textColor: Color = Color.Gray) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        TextComponent(
            textModifier = Modifier.fillMaxWidth().wrapContentHeight(), text = text, fontSize = fontSize, fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6, textColor = textColor, textAlign = textAlign,
            fontWeight = FontWeight.Bold, lineHeight = 30, maxLines = 3,  overflow = TextOverflow.Ellipsis)
    }

}