package components

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansRegular
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max


@OptIn(ExperimentalResourceApi::class)
@Composable
public fun TextComponent(textModifier: Modifier, text: String, fontSize: Int, textStyle: TextStyle, textColor: Color, textAlign: TextAlign, fontWeight: FontWeight?, fontFamily: FontFamily? = null, lineHeight: Int = 10, overflow: TextOverflow = TextOverflow.Clip) {
    Text(text, fontSize = fontSize.sp, fontFamily = fontFamily, modifier = textModifier, style = textStyle, color = textColor, textAlign = textAlign,fontWeight = fontWeight, lineHeight = lineHeight.sp, overflow = overflow)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
public fun TextComponent(text: String, fontSize: Int, textStyle: TextStyle, textColor: Color, textAlign: TextAlign, fontWeight: FontWeight, fontFamily: FontFamily? = null,lineHeight: Int = 10, maxLines: Int = 10, overflow: TextOverflow = TextOverflow.Clip) {
    Text(text, fontSize = fontSize.sp, fontFamily = fontFamily, style = textStyle, color = textColor, textAlign = textAlign,fontWeight = fontWeight, lineHeight = lineHeight.sp, maxLines = maxLines, overflow = overflow)
}

@Composable
public fun TextFieldComponent(text: TextFieldValue, readOnly: Boolean = false, modifier: Modifier, textStyle: TextStyle = LocalTextStyle.current,onValueChange: (TextFieldValue) -> Unit, keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), color: TextFieldColors = TextFieldDefaults.textFieldColors(
    disabledTextColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent
), isSingleLine: Boolean = false, trailingIcon: @Composable (() -> Unit)? = null, isReadOnly: Boolean = false) {

    // for preview add same text to all the fields

    // Normal Text Input field with floating label
    // placeholder is same as hint in xml of edit text
    var mText by remember { mutableStateOf(TextFieldValue(text.text)) }
  /*  TextField(
        value = mText,
        onValueChange = { newValue -> mText = newValue },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text("label") },
        placeholder = { Text("placeholder") },
    )*/

    TextField(value = mText, modifier = modifier, textStyle = textStyle, onValueChange = { newValue -> mText = newValue }, keyboardOptions = keyboardOptions, colors = color, singleLine = isSingleLine, readOnly = isReadOnly, placeholder = {
        PlaceholderTextComponent("Input Phone Number")
    })
}

@OptIn(ExperimentalResourceApi::class)
@Composable
public fun PlaceholderTextComponent(placeholderTile: String) {

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {

        val textStyle: TextStyle = TextStyle(
            fontSize = TextUnit(20f, TextUnitType.Sp),
            fontFamily = GGSansRegular,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal
        )

        TextComponent(
            text = placeholderTile,
            fontSize = 20,
            fontFamily = GGSansRegular,
            textStyle = textStyle,
            textColor = Color.LightGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal
        )

    }
}



