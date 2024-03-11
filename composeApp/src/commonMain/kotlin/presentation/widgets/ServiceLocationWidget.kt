package presentation.widgets

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
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
fun ServiceLocationToggle(mainViewModel: MainViewModel){
    var deliveryType by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .padding(top = 25.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Where do you want your Service?",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 10.dp))

        ToggleButton( shape = RoundedCornerShape(10.dp), onLeftClicked = {
            deliveryType = 0
        }, onRightClicked = {
            deliveryType = 1
        }, leftText = "Parlor", rightText = "Home")


        if(deliveryType == 0) {
            ParlorDeliveryWidget(mainViewModel, 1)
        }
        else{
            HomeDeliveryWidget(mainViewModel, 1)
        }

    }

}
