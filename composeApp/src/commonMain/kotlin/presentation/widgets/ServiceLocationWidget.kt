package presentation.widgets

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import domain.Enums.ServiceLocationEnum
import presentation.components.ToggleButton
import presentation.viewmodels.BookingViewModel
import presentations.components.TextComponent

@Composable
fun ServiceLocationToggle(bookingViewModel: BookingViewModel, isDisabled: Boolean = false, isPackage: Boolean = false, onSpaSelectedListener:() -> Unit,
                          onMobileSelectedListener:() -> Unit){

    var locationType by remember { mutableStateOf(ServiceLocationEnum.SPA.toPath()) }
    val isMobileService = bookingViewModel.currentAppointmentBooking.value.isMobileService
    if(isMobileService){
        locationType = ServiceLocationEnum.MOBILE.toPath()
        onMobileSelectedListener()
    }
    else{
        locationType = ServiceLocationEnum.SPA.toPath()
        onSpaSelectedListener()
    }

    val selectedServiceTypeState = bookingViewModel.selectedServiceType.collectAsState()
    if (!selectedServiceTypeState.value.mobileServiceAvailable){
        locationType = ServiceLocationEnum.SPA.toPath()
        onSpaSelectedListener()
    }

    val isServiceLocationDisabled = if (isPackage){
        isDisabled
    }
    else{
        !selectedServiceTypeState.value.mobileServiceAvailable || isDisabled
    }

    Column(
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Where do you want your Service?",
            fontSize = 16,
            textStyle = MaterialTheme.typography.titleMedium,
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 10.dp))

        ToggleButton(shape = RoundedCornerShape(10.dp), onLeftClicked = {
            onSpaSelectedListener()
            locationType = ServiceLocationEnum.SPA.toPath()
        }, onRightClicked = {
            onMobileSelectedListener()
            locationType = ServiceLocationEnum.MOBILE.toPath()
        }, leftText = "Parlor", rightText = "Home Service", isRightSelection = locationType != ServiceLocationEnum.SPA.toPath(), isDisabled = isServiceLocationDisabled)

    }

}
