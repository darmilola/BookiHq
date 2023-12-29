package features.consultations

import GGSansRegular
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ButtonComponent
import components.GradientButton
import components.TextComponent
import screens.UserProfile.ConnectBusiness.BusinessLogo
import screens.authentication.AuthenticationComposeScreen
import widgets.CartIncrementDecrementWidget
import widgets.TrackOrderProgress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaitingRoomBottomSheet(onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        modifier = Modifier.padding(top = 20.dp),
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        containerColor = Color(0xFFF3F3F3),
        dragHandle = { },
    ) {
        SheetContent()
    }
}


@Composable
fun SheetContent() {
    Column(
        Modifier
            .padding(bottom = 10.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.60f),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {
        WaitingRoomSheetContentHeader()

        TextComponent(
            text = "Lorem ipsum dolor sit amet consectetuer adipiscing Aenean ipsum dolor sit amet consectetuer",
            fontSize = 25,
            fontFamily = GGSansRegular,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Black,
            lineHeight = 35,
            textModifier = Modifier.fillMaxWidth().padding(bottom = 15.dp)
        )

        Row(
            modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BusinessLogo(size = 45)

            TextComponent(
                text = "This is the Business name",
                fontSize = 23,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Black,
                lineHeight = 25,
                textModifier = Modifier.wrapContentWidth().padding(bottom = 15.dp, top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
            TextComponent(
                textModifier = Modifier.fillMaxWidth().padding(bottom = 15.dp), text = "Scheduled 3 pm, 29 December, 2023", fontSize = 20, fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6, textColor = Color.Gray, textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold, lineHeight = 30, overflow = TextOverflow.Ellipsis)

            AttachActionButtons()
        }
    }




@Composable
fun WaitingRoomSheetContentHeader() {
    Row(
        modifier = Modifier.height(60.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {

        Box(
            Modifier
                .fillMaxWidth()
                .height(25.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.width(60.dp).height(5.dp).clip(CircleShape)
                    .background(color = Color.LightGray)
            )
        }

        Box(
            modifier = Modifier
                .padding(end = 10.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1.5f),
            contentAlignment = Alignment.CenterEnd
        ) {

        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AttachActionButtons(){

    val navigator = LocalNavigator.currentOrThrow

    val bgStyle = Modifier
        .padding(bottom = 40.dp, top = 20.dp)
        .fillMaxWidth()
        .wrapContentHeight()

    val coroutineScope = rememberCoroutineScope()

    val buttonStyle = Modifier
        .padding(bottom = 10.dp)
        .fillMaxWidth(0.90f)
        .height(56.dp)

    val gradientButtonStyle = Modifier
        .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
        .fillMaxWidth()
        .height(56.dp)

    Column(modifier = bgStyle, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        ButtonComponent(modifier = buttonStyle, buttonText = "Test Mic And Speaker", borderStroke = BorderStroke(1.dp, Color(color = 0xFFF43569)), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(28.dp), textColor = Color(color = 0xFFF43569), style = TextStyle()){}
        GradientButton(modifier = gradientButtonStyle, buttonText = "Join Meeting", borderStroke = null, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(28.dp), textColor = Color.White, style = TextStyle(), gradient =  Brush.horizontalGradient(
            colors = listOf(
                Color(color = 0xFFF43569),
                Color(color = 0xFFFF823E)
            )
        )){

        }

    }

}


