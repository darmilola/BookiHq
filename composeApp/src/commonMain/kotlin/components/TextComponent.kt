package components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalResourceApi::class)
@Composable
public fun TextComponent(textModifier: Modifier, text: String, fontSize: Int, textStyle: TextStyle, textColor: Color, textAlign: TextAlign, fontWeight: FontWeight) {
    Text(text, fontSize = fontSize.sp, modifier = textModifier, style = textStyle, color = textColor, textAlign = textAlign,fontWeight = fontWeight, lineHeight = 25.sp)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
public fun TextComponent(text: String, fontSize: Int, textStyle: TextStyle, textColor: Color, textAlign: TextAlign, fontWeight: FontWeight) {
    Text(text, fontSize = fontSize.sp, style = textStyle, color = textColor, textAlign = textAlign,fontWeight = fontWeight, lineHeight = 25.sp)
}