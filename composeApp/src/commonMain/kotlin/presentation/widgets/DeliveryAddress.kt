package presentation.widgets

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Models.DeliveryLocation
import presentation.components.ToggleButton
import presentation.viewmodels.CartViewModel
import presentation.viewmodels.MainViewModel
import presentations.components.TextComponent

@Composable
fun ProductDeliveryAddressWidget(mainViewModel: MainViewModel,cartViewModel: CartViewModel,
                                 onPickupSelectedListener:() -> Unit, onHomeSelectedListener:() -> Unit){

    val deliveryLocation =  remember { mutableStateOf(cartViewModel.deliveryLocation.value) }
    val columnModifier = Modifier
        .padding(start = 10.dp, bottom = 10.dp, top = 15.dp, end = 10.dp)
        .wrapContentHeight()
        .fillMaxWidth()
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start, modifier = columnModifier) {
        TextComponent(
            text = "Delivery Location",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            lineHeight = 20,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textModifier = Modifier.padding(start = 10.dp, top = 10.dp)
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            ToggleButton(shape = CircleShape, onLeftClicked = {
                deliveryLocation.value = DeliveryLocation.HOME_DELIVERY.toPath()
                onHomeSelectedListener()
            }, onRightClicked = {
                deliveryLocation.value = DeliveryLocation.PICKUP.toPath()
                onPickupSelectedListener()
            }, leftText = "Home", rightText = "Pick Up")
        }

       if(deliveryLocation.value == DeliveryLocation.HOME_DELIVERY.toPath()) {
            HomeDeliveryWidget(mainViewModel = mainViewModel)
       }
        else{
            ParlorDeliveryWidget(mainViewModel)
        }


    }

}