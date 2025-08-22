package presentation.widgets

import GGSansSemiBold
import theme.styles.Colors
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
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
import cafe.adriel.voyager.navigator.OnBackPressed
import presentations.components.ImageComponent
import presentations.components.TextFieldComponent
import presentations.components.TextFieldComponentV2

@Composable
fun SearchBarWidget(iconRes: String, placeholderText: String, iconSize: Int, onBackPressed:() -> Unit, keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    isPasswordField: Boolean = false, onValueChange: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    var iconResValue by remember { mutableStateOf(iconRes) }
    var borderStroke by remember { mutableStateOf(BorderStroke(1.dp, color  = Color.Gray)) }

    if (text.isNotEmpty()){
        iconResValue = "drawable/back_arrow.png"
    }

    val modifier  = Modifier
        .padding(end = 10.dp, start = 10.dp, top = 10.dp)
        .fillMaxWidth()
        .height(50.dp)
        .border(border = borderStroke, shape = CircleShape)
        .background(color = Color.Transparent, shape = CircleShape)

    Row(modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically) {

        Box(modifier = Modifier.fillMaxHeight().width(50.dp), contentAlignment = Alignment.Center){
            ImageComponent(imageModifier = Modifier
                .size(iconSize.dp).clickable {
                      if(iconResValue == "drawable/back_arrow.png"){
                          text = ""
                          iconResValue = "drawable/search_icon.png"
                          onBackPressed()
                      }
                }, imageRes = iconResValue, colorFilter = ColorFilter.tint(color = Colors.primaryColor))
        }
        TextFieldComponentV2(
            text = text,
            readOnly = false,
            textStyle = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(end = 10.dp),
            keyboardOptions = keyboardOptions,
            onValueChange = {
                text = it
                onValueChange(it)
            } , isSingleLine = true, placeholderText = placeholderText, onFocusChange = { it ->
                borderStroke = if (it){
                    BorderStroke(1.dp, color  = Colors.primaryColor)
                } else{
                    BorderStroke(1.dp, color  = Color.Gray)
                }
            },
            isPasswordField = isPasswordField,
            placeholderTextSize = 17f
        )
    }
}
