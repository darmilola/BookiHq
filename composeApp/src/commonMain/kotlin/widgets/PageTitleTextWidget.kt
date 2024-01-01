package widgets

import GGSansBold
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import components.TextComponent

@Composable
fun TitleWidget(title: String, textColor: Color, ){
        TextComponent(
            text = title,
            fontSize = 25,
            fontFamily = GGSansBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = textColor,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Normal,
            lineHeight = 30,
            textModifier = Modifier.wrapContentSize()
        )
}

