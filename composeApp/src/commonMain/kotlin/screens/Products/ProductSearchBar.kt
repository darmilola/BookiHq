package screens.Products

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansSemiBold
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import components.IconTextFieldComponent

@Composable
fun ProductSearchBar(){
    var text by remember { mutableStateOf(TextFieldValue("")) }


    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        val textStyle: TextStyle = TextStyle(
            fontSize = TextUnit(20f, TextUnitType.Sp),
            fontFamily = GGSansSemiBold,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal
        )

        val modifier  = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 15.dp)
            .wrapContentWidth()
            .height(55.dp)
            .border(width = 1.dp, color = Color.Gray, shape =  RoundedCornerShape(30.dp))


        Box(modifier = modifier) {
            IconTextFieldComponent(
                text = text,
                readOnly = false,
                textStyle = textStyle,
                modifier = Modifier,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = { it ->
                    text = it
                }, isSingleLine = true, iconRes = "search_icon.png"
            )
        }
    }
}
