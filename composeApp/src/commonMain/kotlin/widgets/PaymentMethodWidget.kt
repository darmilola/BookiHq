package widgets

import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.IconTextComponent
import components.RadioToggleButton
import components.TextComponent
import components.ToggleButton
import screens.consultation.ConsultLocationToggle
import screens.consultation.WelcomeUser

@Composable
fun PaymentMethodWidget() {
    val toggleLabelList: ArrayList<String> = arrayListOf()
    toggleLabelList.add("Credit/Debit Card")
    toggleLabelList.add("Cash On Delivery")
    toggleLabelList.add("Any Other Payment Method Available")

    RadioToggleButton(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(27.dp), style = MaterialTheme.typography.h4, actionLabel = toggleLabelList, title = "Payment Method", gridCount = 1)

}