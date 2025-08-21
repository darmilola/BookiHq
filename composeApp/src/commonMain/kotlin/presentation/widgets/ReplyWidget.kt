package presentation.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import presentations.components.ImageComponent
import presentations.components.TextFieldComponent
import presentations.components.TextFieldComponentV2
import theme.Colors

@Composable
fun ReplyWidget(iconRes: String, placeholderText: String, defaultValue: String = "", iconSize: Int, keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), isFocusedByDefault: Boolean = false, viewHeight: Int = 50, isSingleLine: Boolean = true, maxLines: Int = 1, mBorderStroke: BorderStroke =  BorderStroke(2.dp, color  = Color.Transparent), onValueChange: ((String) -> Unit)? = null) {
    var text by remember { mutableStateOf(defaultValue) }
    val isFocused = remember { mutableStateOf(false) }
    var borderStroke by remember { mutableStateOf(mBorderStroke) }

    if(isFocusedByDefault){
        borderStroke =  BorderStroke(2.dp, color  = Colors.primaryColor)
    }

    val modifier  = Modifier
        .padding(end = 10.dp, start = 10.dp)
        .fillMaxWidth()
        .height(viewHeight.dp)
        .border(border = if (isFocused.value) BorderStroke(1.dp, color = Colors.primaryColor) else  BorderStroke(2.dp, color = Color.Transparent), shape = CircleShape)
        .background(color = Colors.lightPrimaryColor, shape = CircleShape)

    Row(modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top) {
        TextFieldComponentV2(
            text = text,
            readOnly = false,
            textStyle = TextStyle(
                fontSize = TextUnit(18f, TextUnitType.Sp),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                color = Color.Gray
            ),
            modifier = Modifier.fillMaxHeight().fillMaxWidth(0.85f).padding(start = 15.dp),
            keyboardOptions = keyboardOptions,
            onValueChange = {
                text = it
                if (onValueChange != null) {
                    onValueChange(text)
                }
            },
            isSingleLine = isSingleLine,
            placeholderText = placeholderText,
            onFocusChange = { it ->
                isFocused.value = it
            },
            isPasswordField = false,
            placeholderTextSize = 16f,
            maxLines = maxLines
        )
        Box(modifier = Modifier.fillMaxHeight().width(50.dp), contentAlignment = Alignment.Center) {
            ImageComponent(
                imageModifier = Modifier
                    .size(iconSize.dp),
                imageRes = iconRes,
                colorFilter = ColorFilter.tint(color = Colors.primaryColor)
            )
        }
    }
}

