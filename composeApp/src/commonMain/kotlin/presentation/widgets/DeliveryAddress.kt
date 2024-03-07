package presentation.widgets

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentation.components.ToggleButton
import presentation.viewmodels.MainViewModel
import presentations.components.TextComponent

@Composable
fun ProductDeliveryAddressWidget(mainViewModel: MainViewModel) {
    var deliveryType by remember { mutableStateOf(0) }
    val columnModifier = Modifier
        .padding(start = 10.dp, bottom = 10.dp, top = 15.dp, end = 10.dp)
        .wrapContentHeight()
        .fillMaxWidth()
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
        TextComponent(
            text = "Delivery Location",
            fontSize = 20,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Black,
            textModifier = Modifier.padding(start = 10.dp, top = 10.dp)
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            ToggleButton(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(10.dp), style = MaterialTheme.typography.h4, onLeftClicked = {
                deliveryType = 0
            }, onRightClicked = {
                deliveryType = 1
            }, leftText = "Home", rightText = "Pick Up")
        }

       if(deliveryType == 0) {
            HomeDeliveryWidget(mainViewModel = mainViewModel, fromView = 3)
       }
        else{
            ParlorDeliveryWidget(mainViewModel, fromView = 3)
        }


    }

}