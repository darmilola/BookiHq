package widgets

import GGSansRegular
import GGSansSemiBold
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
import components.TextComponent

@Composable
fun PageSubtitleTextWidget(subtitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        val modifier = Modifier
            .fillMaxWidth()
        TextComponent(
            textModifier = Modifier.fillMaxWidth(), text = subtitle, fontSize = 18, fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6, textColor = Color.Gray, textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold, lineHeight = 23, maxLines = 3,  overflow = TextOverflow.Ellipsis)
    }

}