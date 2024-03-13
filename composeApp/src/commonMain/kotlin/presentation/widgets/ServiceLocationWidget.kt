package presentation.widgets

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentation.components.ToggleButton
import presentation.viewmodels.MainViewModel
import presentations.components.TextComponent

@Composable
fun ServiceLocationToggle(mainViewModel: MainViewModel){
    val selectedServiceTypeState = mainViewModel.selectedServiceType.collectAsState()
    var locationType by remember { mutableStateOf(0) }
    if (!selectedServiceTypeState.value.homeServiceAvailable){
        locationType = 0
    }
    Column(
        modifier = Modifier
            .padding(top = 35.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Where do you want your Service?",
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 10.dp))

        ToggleButton( shape = CircleShape, onLeftClicked = {
            locationType = 0
        }, onRightClicked = {
            locationType = 1
        }, leftText = "Parlor", rightText = "Home", isDisabled = !selectedServiceTypeState.value.homeServiceAvailable)


        if(locationType == 0) {
            ParlorDeliveryWidget(mainViewModel, 1)
        }
        else{
            HomeDeliveryWidget(mainViewModel, 1)
        }

    }

}
