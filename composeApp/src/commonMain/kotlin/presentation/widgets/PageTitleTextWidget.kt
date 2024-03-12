package presentation.widgets

import GGSansBold
import GGSansRegular
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import presentations.components.TextComponent

@Composable
fun TitleWidget(title: String, textColor: Color, fonSize: Int = 22, textModifier: Modifier = Modifier.wrapContentSize()){
        TextComponent(
            text = title,
            fontSize = fonSize,
            fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6,
            textColor = textColor,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 30,
            maxLines = 1,
            textModifier = textModifier
        )
}


