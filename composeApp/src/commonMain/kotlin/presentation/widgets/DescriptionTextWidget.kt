package presentation.widgets

import GGSansRegular
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import presentations.components.TextComponent

@Composable
fun DescriptionTextWidget(text: String, fontSize: Int = 23, textAlign: TextAlign = TextAlign.Left, textColor: Color = Color.DarkGray) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        TextComponent(
            textModifier = Modifier.fillMaxWidth().wrapContentHeight(), text = text, fontSize = fontSize, fontFamily = GGSansRegular,
            textStyle = TextStyle(), textColor = textColor, textAlign = textAlign,
            fontWeight = FontWeight.Black, lineHeight = 35, maxLines = Int.MAX_VALUE,  overflow = TextOverflow.Ellipsis)
    }

}