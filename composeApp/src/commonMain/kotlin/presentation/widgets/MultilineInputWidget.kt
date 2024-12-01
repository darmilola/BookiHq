package presentation.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import presentations.components.MultilineTextFieldComponent
import presentations.components.TextFieldComponent
import theme.Colors

@Composable
fun MultilineInputWidget(defaultValue: String = "", keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), isFocusedByDefault: Boolean = false, viewHeight: Int = 70, maxLines: Int = 10, mBorderStroke: BorderStroke =  BorderStroke(2.dp, color  = Color.Transparent), onValueChange: ((String) -> Unit)? = null) {
    var text by remember { mutableStateOf(defaultValue) }
    var borderStroke by remember { mutableStateOf(mBorderStroke) }

    if(isFocusedByDefault){
        borderStroke =  BorderStroke(2.dp, color  = Colors.primaryColor)
    }

    val modifier  = Modifier
        .padding(end = 10.dp, start = 10.dp, top = 15.dp)
        .fillMaxWidth()
        .height(viewHeight.dp)
        .border(border = borderStroke, shape = RoundedCornerShape(10.dp))
        .background(color = Colors.lightPrimaryColor, shape = RoundedCornerShape(10.dp))

    Row(modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top) {
        MultilineTextFieldComponent(
            text = text,
            readOnly = false,
            textStyle = TextStyle(
                fontSize = TextUnit(18f, TextUnitType.Sp),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                color = Color.Gray
            ),
            modifier = Modifier.fillMaxHeight().fillMaxWidth(0.90f).padding(end = 15.dp, start = 15.dp, top = 15.dp, bottom = 15.dp),
            keyboardOptions = keyboardOptions,
            onValueChange = {
                text = it
                if (onValueChange != null) {
                    onValueChange(text)
                }
            },
            isSingleLine = false,
            placeholderText = "Type here...",
            onFocusChange = { it ->
                borderStroke = if (it) {
                    BorderStroke(2.dp, color = Colors.primaryColor)
                } else {
                    BorderStroke(2.dp, color = Color.Transparent)
                }
            },
            isPasswordField = false,
            placeholderTextSize = 16f,
            maxLines = maxLines
        )
    }
}

