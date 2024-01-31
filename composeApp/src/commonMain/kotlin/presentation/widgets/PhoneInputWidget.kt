package presentation.widgets

import GGSansSemiBold
import domain.Models.PhoneExtensionModel
import theme.styles.Colors
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import presentation.components.TextFieldComponent

@Composable
fun PhoneInputWidget() {
    val countryCodeList = listOf<PhoneExtensionModel>(PhoneExtensionModel(countryCode = "+ 234", countryFlagRes = "drawable/nigeria_flag_icon.png"), PhoneExtensionModel(countryCode = "+ 27", countryFlagRes = "drawable/south_africa_flag.png"))
    var text by remember { mutableStateOf("") }
    var borderStroke by remember { mutableStateOf(BorderStroke(2.dp, color  = Color.Transparent)) }

    val modifier  = Modifier
        .padding(end = 10.dp, start = 10.dp, top = 20.dp)
        .fillMaxWidth()
        .height(70.dp)
        .border(border = borderStroke, shape = RoundedCornerShape(15.dp))
        .background(color = Colors.lightPrimaryColor, shape = RoundedCornerShape(15.dp))

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(modifier = Modifier.fillMaxWidth(0.30f).fillMaxHeight().padding(start = 20.dp), contentAlignment = Alignment.Center) {
            CountryCodeDropDownWidget(menuItems = countryCodeList)
        }

        Box(modifier = Modifier.fillMaxWidth(0.10f).fillMaxHeight(), contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.width(2.dp).fillMaxHeight(0.40f).background(color = Color.DarkGray)) }

        TextFieldComponent(
            text = text,
            readOnly = false,
            textStyle = TextStyle(fontSize = TextUnit(23f, TextUnitType.Sp), fontFamily = GGSansSemiBold, fontWeight = FontWeight.Normal, textAlign = TextAlign.Start, color = Color.DarkGray),
            modifier = Modifier.wrapContentSize(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                text = it
            } , isSingleLine = true, placeholderText = "000 0000 000", onFocusChange = {
                    it ->
                borderStroke = if (it){
                    BorderStroke(2.dp, color  = Colors.primaryColor)
                } else{
                    BorderStroke(2.dp, color  = Color.Transparent)
                }
            },
            isPasswordField = false, placeholderTextSize = 25f
        )
    }
}