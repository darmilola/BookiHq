package widgets

import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import components.IconTextComponent
import components.TextComponent
import components.TextFieldComponent
import components.ToggleButton

@Composable
fun DeliveryAddressWidget() {
    val columnModifier = Modifier
        .padding(start = 10.dp, bottom = 10.dp)
        .height(250.dp)
        .fillMaxWidth()
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
        TextComponent(
            text = "Delivery To",
            fontSize = 20,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Black,
            textModifier = Modifier.padding(start = 10.dp, top = 10.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(0.7f)) {
            ToggleButton(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(10.dp), style = MaterialTheme.typography.h4, onLeftClicked = {

            }, onRightClicked = {

            }, leftText = "Home", rightText = "Pick Up")
        }

        Column (modifier = Modifier.fillMaxWidth().padding(start = 10.dp)){
            IconTextComponent(
                text = "Ahmed Ansari is the Name",
                textStyle = TextStyle(),
                fontSize = 19,
                fontFamily = GGSansRegular,
                fontWeight = FontWeight.ExtraBold,
                imageModifier = Modifier.size(24.dp),
                imageRes = "drawable/user_icon_outline.png",
                textAlign = TextAlign.Left,
                textColor = Color(color = 0xff727272),
                colorFilter = ColorFilter.tint(color = Color(color = 0xff727272)))

            IconTextComponent(
                text = "Ibeju Lekki 105101, Lagos",
                textStyle = TextStyle(),
                fontSize = 19,
                fontFamily = GGSansRegular,
                fontWeight = FontWeight.ExtraBold,
                imageModifier = Modifier.size(24.dp),
                imageRes = "drawable/location_icon.png",
                textAlign = TextAlign.Left,
                textColor = Color(color = 0xff727272),
                colorFilter = ColorFilter.tint(color = Color(color = 0xff727272)))

            IconTextComponent(
                text = "+234 (810)2853533",
                textStyle = TextStyle(),
                fontSize = 19,
                fontFamily = GGSansRegular,
                fontWeight = FontWeight.ExtraBold,
                imageModifier = Modifier.size(24.dp),
                imageRes = "drawable/phone_icon.png",
                textAlign = TextAlign.Left,
                textColor = Color(color = 0xff727272),
                colorFilter = ColorFilter.tint(color = Color(color = 0xff727272)))
        }

    }

}