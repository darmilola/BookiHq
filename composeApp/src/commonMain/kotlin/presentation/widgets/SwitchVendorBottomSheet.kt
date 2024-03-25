package presentation.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.Models.Auth0ConnectionType
import kotlinx.datetime.LocalDate
import presentation.components.ButtonComponent
import presentation.components.IconButtonComponent
import presentation.components.bottomSheetIconButtonComponent
import presentations.components.TextComponent
import theme.styles.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwitchVendorBottomSheet() {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    ModalBottomSheet(
        modifier = Modifier.padding(top = 20.dp),
        onDismissRequest = { },
        sheetState = modalBottomSheetState,
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        containerColor = Color(0xFFF3F3F3),
        dragHandle = {},
    ) {
        SwitchVendorBottomSheetContent(onDismissRequest = {

        }, onConfirmation = {

        })
    }
}


@Composable
fun SwitchVendorBottomSheetContent(onDismissRequest: () -> Unit,
                                      onConfirmation: () -> Unit) {

    Card(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        shape = RoundedCornerShape(10.dp),
        elevation = 15.dp, border = BorderStroke((0.5).dp, color = Colors.primaryColor)
    ) {
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {

            Box(modifier = Modifier.fillMaxWidth().height(90.dp).background(color = Colors.primaryColor), contentAlignment = Alignment.Center) {
                TitleWidget(title = "Why decide to make this decision now.", textColor = Color.White)

            }
            SwitchVendorBottomSheet()

        }
    }

}

@Composable
fun switchVendorReasons(){
    val buttonStyle = Modifier
        .padding(bottom = 15.dp)
        .fillMaxWidth(0.95f)
        .height(45.dp)
    bottomSheetIconButtonComponent(modifier = buttonStyle, buttonText = "Not So Good Service", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 14, shape = RoundedCornerShape(15.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "drawable/x_logo.png",  colorFilter = ColorFilter.tint(color = Color.DarkGray)){}
    bottomSheetIconButtonComponent(modifier = buttonStyle, buttonText = "Not So Good Service", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 14, shape = RoundedCornerShape(15.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "drawable/x_logo.png",  colorFilter = ColorFilter.tint(color = Color.DarkGray)){}
    bottomSheetIconButtonComponent(modifier = buttonStyle, buttonText = "Not So Good Service", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 14, shape = RoundedCornerShape(15.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "drawable/x_logo.png",  colorFilter = ColorFilter.tint(color = Color.DarkGray)){}
    bottomSheetIconButtonComponent(modifier = buttonStyle, buttonText = "Not So Good Service", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 14, shape = RoundedCornerShape(15.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "drawable/x_logo.png",  colorFilter = ColorFilter.tint(color = Color.DarkGray)){}
    bottomSheetIconButtonComponent(modifier = buttonStyle, buttonText = "Not So Good Service", borderStroke = BorderStroke(0.8.dp, Color.LightGray), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 14, shape = RoundedCornerShape(15.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "drawable/x_logo.png",  colorFilter = ColorFilter.tint(color = Color.DarkGray)){}
}
