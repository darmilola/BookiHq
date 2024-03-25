package presentation.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
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
fun SwitchVendorBottomSheet(onDismiss: () -> Unit,
                            onConfirmation: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        modifier = Modifier.padding(top = 20.dp),
        onDismissRequest = {
                onDismiss()
        },
        sheetState = modalBottomSheetState,
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        containerColor = Color.White,
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
        shape = RoundedCornerShape(20.dp),
        elevation = 15.dp) {
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            SwitchVendorBottomSheetContentHeader()
            Box(modifier = Modifier.fillMaxWidth().height(60.dp).background(color = Color.White), contentAlignment = Alignment.Center) {
                TitleWidget(title = "Why decide to make this decision?", textColor = Colors.darkPrimary,fonSize = 18)

            }
            Column (modifier = Modifier.fillMaxWidth().height(350.dp).padding(start = 15.dp, end = 15.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                switchVendorReasons()
            }
        }
    }

}

@Composable
fun SwitchVendorBottomSheetContentHeader() {
    Row(modifier = Modifier.height(30.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier
            .padding(start = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f),
            contentAlignment = Alignment.CenterStart) {

        }
        Box(Modifier.weight(2.5f), contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFFE9F0F2))
                    .height(4.dp)
                    .width(40.dp)
            )
        }
        Box(modifier = Modifier
            .padding(end = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f),
            contentAlignment = Alignment.CenterEnd) {

        }
    }
}

@Composable
fun switchVendorReasons(){
    val buttonStyle = Modifier
        .padding(bottom = 10.dp)
        .fillMaxWidth()
        .height(60.dp)
    bottomSheetIconButtonComponent(modifier = buttonStyle, buttonText = "Bad Therapist", borderStroke = BorderStroke(0.8.dp, Color(0xFFE9F0F2)), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 14, shape = RoundedCornerShape(15.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "drawable/emoji_frowned.png"){}
    bottomSheetIconButtonComponent(modifier = buttonStyle, buttonText = "Too Expensive", borderStroke = BorderStroke(0.8.dp, Color(0xFFE9F0F2)), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 14, shape = RoundedCornerShape(15.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "drawable/emoji_think.png"){}
    bottomSheetIconButtonComponent(modifier = buttonStyle, buttonText = "Needed More Service", borderStroke = BorderStroke(0.8.dp, Color(0xFFE9F0F2)), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 14, shape = RoundedCornerShape(15.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "drawable/emoji_frowned.png"){}
    bottomSheetIconButtonComponent(modifier = buttonStyle, buttonText = "Need Something New", borderStroke = BorderStroke(0.8.dp, Color(0xFFE9F0F2)), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 14, shape = RoundedCornerShape(15.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "drawable/emoji_frowned.png"){}
    bottomSheetIconButtonComponent(modifier = buttonStyle, buttonText = "Prefer not to say", borderStroke = BorderStroke(0.8.dp, Color(0xFFE9F0F2)), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 14, shape = RoundedCornerShape(15.dp), textColor = Color.DarkGray, style = MaterialTheme.typography.h4, iconRes = "drawable/emoji_think.png"){}
}
