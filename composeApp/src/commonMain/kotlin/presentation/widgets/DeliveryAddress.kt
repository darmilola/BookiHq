package presentation.widgets

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Enums.DeliveryMethodEnum
import presentation.components.ToggleButton
import presentation.viewmodels.CartViewModel
import presentation.viewmodels.MainViewModel
import presentations.components.TextComponent
import utils.getDeliveryMethodDisplayName

@Composable
fun ProductDeliveryAddressWidget(cartViewModel: CartViewModel, isDisabled: Boolean = false,
                                 onPickupSelectedListener:() -> Unit, onMobileSelectedListener:() -> Unit){

    val deliveryMethod =  cartViewModel.deliveryMethod.collectAsState()
    val isRightSelection = deliveryMethod.value == DeliveryMethodEnum.MOBILE.toPath()
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
            ToggleButton(shape = CircleShape, isRightSelection = isRightSelection, onLeftClicked = {
                onPickupSelectedListener()
            }, onRightClicked = {
                onMobileSelectedListener()
            },
                isDisabled = isDisabled,
                rightText = getDeliveryMethodDisplayName(DeliveryMethodEnum.MOBILE.toPath()), leftText = getDeliveryMethodDisplayName(DeliveryMethodEnum.PICKUP.toPath()))
        }

    }

}