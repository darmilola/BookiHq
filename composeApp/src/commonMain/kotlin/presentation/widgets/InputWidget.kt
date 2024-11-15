package presentations.widgets

import theme.Colors
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
import presentations.components.TextFieldComponent


@Composable
fun InputWidget(iconRes: String, maxLength: Int, placeholderText: String, text: String = "", iconSize: Int, keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), isFocusedByDefault: Boolean = false, viewHeight: Int = 70, isSingleLine: Boolean = false, maxLines: Int = 1, mBorderStroke: BorderStroke =  BorderStroke(1.dp, color  = Color.Transparent), isPasswordField: Boolean = false, onSaveClicked: Boolean = false, onValueChange: ((String) -> Unit)? = null) {
    var passwordIconRes by remember { mutableStateOf("drawable/not_visible.png") }
    var borderStroke by remember { mutableStateOf(mBorderStroke) }
    var iconTint by remember { mutableStateOf(Colors.primaryColor) }
    if (onSaveClicked){
        borderStroke = if (text.isEmpty()) {
            BorderStroke(2.dp, color = Colors.pinkColor)
        } else {
            BorderStroke(2.dp, color = Color.Transparent)
        }
        iconTint = if (text.isEmpty()){
            Colors.pinkColor
        }
        else{
            Colors.primaryColor
        }
    }

    if(isFocusedByDefault){
        borderStroke =  BorderStroke(1.dp, color  = Colors.primaryColor)
    }

    var isPassword by remember { mutableStateOf(isPasswordField) }

    val modifier  = Modifier
        .padding(top = 20.dp)
        .fillMaxWidth()
        .height(viewHeight.dp)
        .border(border = borderStroke, shape = RoundedCornerShape(10.dp))
        .background(color = Colors.lightPrimaryColor, shape = RoundedCornerShape(10.dp))

    Row(modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top) {

        Box(modifier = Modifier.fillMaxHeight().width(50.dp), contentAlignment = Alignment.Center) {
            ImageComponent(
                imageModifier = Modifier
                    .size(iconSize.dp),
                imageRes = iconRes,
                colorFilter = ColorFilter.tint(color = iconTint)
            )
        }
        TextFieldComponent(
            text = text,
            readOnly = false,
            textStyle = TextStyle(
                fontSize = TextUnit(18f, TextUnitType.Sp),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                color = Color.Gray
            ),
            modifier = Modifier.fillMaxHeight().fillMaxWidth(0.85f).padding(end = 10.dp),
            keyboardOptions = keyboardOptions,
            onValueChange = {
                if (onValueChange != null) {
                    if (it.trim().length < maxLength) {
                        onValueChange(it)
                    }
                }
            },
            isSingleLine = isSingleLine,
            placeholderText = placeholderText,
            onFocusChange = { it ->
                borderStroke = if (it) {
                    BorderStroke(2.dp, color = Colors.primaryColor)
                } else {
                    BorderStroke(2.dp, color = Color.Transparent)
                }
            },
            isPasswordField = isPassword,
            placeholderTextSize = 16f,
            maxLines = maxLines
        )
        if (isPassword) {
            Box(
                modifier = Modifier.fillMaxHeight().width(50.dp),
                contentAlignment = Alignment.Center
            ) {
                ImageComponent(
                    imageModifier = Modifier
                        .size(iconSize.dp).clickable {
                            isPassword = !isPassword
                            passwordIconRes = if(isPassword){
                                "drawable/not_visible.png"
                            } else{
                                "drawable/visible.png"
                            }
                        },
                    imageRes = passwordIconRes,
                    colorFilter = ColorFilter.tint(color = Colors.primaryColor)
                )
            }
        }
    }
}

