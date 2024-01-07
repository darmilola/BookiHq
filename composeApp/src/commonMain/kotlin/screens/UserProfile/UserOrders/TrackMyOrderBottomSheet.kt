package screens.UserProfile.UserOrders

import GGSansRegular
import Styles.Colors
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.TextComponent
import widgets.TrackOrderProgress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackMyOrderBottomSheet(onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        modifier = Modifier.padding(top = 20.dp),
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        containerColor = Color.White,
        dragHandle = { },
    ) {
        SheetContent()
    }
}


@Composable
fun SheetContent() {
    Column(
        Modifier
            .padding(bottom = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.Start,
    ) {
        SheetContentHeader()
        TextComponent(
            text = "Track My Order",
            fontSize = 30,
            fontFamily = GGSansRegular,
            textStyle = TextStyle(),
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 35,
            textModifier = Modifier.wrapContentSize().padding(start = 15.dp, top = 10.dp)
        )

        Row(modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(top = 20.dp)) {
            TrackOrderProgress(modifier = Modifier.fillMaxWidth().fillMaxHeight(), numberOfSteps = 3, currentOrderProgress = 1)
        }
    }
}



@Composable
fun SheetContentHeader() {
    Row (modifier = Modifier.height(60.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top) {
        Box(modifier = Modifier
            .height(120.dp)
            .weight(1.5f)
            .padding(start = 10.dp)) {

            TextComponent(
                text = "Cancel",
                fontSize = 20,
                fontFamily = GGSansRegular,
                textStyle = TextStyle(),
                textColor = Colors.primaryColor,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Black,
                lineHeight = 35,
                textModifier = Modifier.wrapContentSize().padding(start = 15.dp, top = 25.dp)
            )

        }

        Box(Modifier.weight(2f)
            .fillMaxWidth()
            .height(25.dp),
            contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.width(40.dp).height(7.dp).clip(CircleShape).background(color = Color.LightGray))
        }

        Box(modifier = Modifier
            .padding(end = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1.5f),
            contentAlignment = Alignment.CenterEnd) {

        }
    }
}