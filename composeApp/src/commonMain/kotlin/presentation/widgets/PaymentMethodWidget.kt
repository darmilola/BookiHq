package presentation.widgets

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import presentation.components.RadioToggleButton

@Composable
fun PaymentMethodWidget() {
    val toggleLabelList: ArrayList<String> = arrayListOf()
    toggleLabelList.add("Credit/Debit Card")
    toggleLabelList.add("Cash On Delivery")

    RadioToggleButton(shape = RoundedCornerShape(27.dp), actionLabel = toggleLabelList, title = "Payment Method", gridCount = 1)

}