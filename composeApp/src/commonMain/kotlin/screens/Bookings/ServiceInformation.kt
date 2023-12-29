package screens.Bookings

import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ButtonComponent
import components.ImageComponent
import components.TextComponent
import kotlinx.coroutines.launch
import screens.UserProfile.ConnectBusiness.AttachActionButtons
import screens.UserProfile.ConnectBusiness.AttachLocationIcon
import screens.UserProfile.ConnectBusiness.AttachLocationInfoIcon
import screens.UserProfile.ConnectBusiness.BusinessInfoContent
import screens.UserProfile.ConnectBusiness.BusinessInfoTitle
import screens.UserProfile.ConnectBusiness.BusinessLogo

object ServiceInformationPage : Screen {
    @Composable
    override fun Content() {
        Scaffold(
            topBar = {
                ServiceInfoTitle()
            },
            content = {
                ServiceInfoContent()

             },
            backgroundColor = Color(0xFFF3F3F3),
        )
    }
}

@Composable
fun ServiceInfoTitle(){
    val rowModifier = Modifier
        .fillMaxWidth()
        .height(40.dp)

    val colModifier = Modifier
        .padding(top = 55.dp, end = 0.dp)
        .fillMaxWidth()
        .height(40.dp)

    Column(modifier = colModifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart) {
                leftTopBarItem()
            }

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                TextComponent(
                    text = "Service Details",
                    fontSize = 30,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                )
            }

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth(0.20f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
            }
        }
    }
}

@Composable
fun leftTopBarItem() {
    val navigator = LocalNavigator.currentOrThrow
    val modifier = Modifier
        .padding(start = 15.dp, top = 3.dp)
        .clickable {
            navigator.pop()
        }
        .size(20.dp)
    ImageComponent(imageModifier = modifier, imageRes = "drawable/solid_back_icon.png", colorFilter = ColorFilter.tint(color = Color.DarkGray))
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ServiceInfoContent() {
    val columnModifier = Modifier
        .padding(start = 15.dp, end = 15.dp, top = 50.dp)
        .fillMaxHeight()
        .fillMaxWidth()
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
         Column(
                modifier = columnModifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {

             TextComponent(
                 text = "Lorem ipsum dolor sit amet consectetuer adipiscing Aenean",
                 fontSize = 30,
                 fontFamily = GGSansRegular,
                 textStyle = MaterialTheme.typography.h6,
                 textColor = Color.DarkGray,
                 textAlign = TextAlign.Left,
                 fontWeight = FontWeight.Black,
                 lineHeight = 35,
                 textModifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
             )

                Row(modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
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
                        textModifier = Modifier.fillMaxWidth().padding(bottom = 15.dp, top = 10.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis)
                }

                TextComponent(
                    textModifier = Modifier.fillMaxWidth().padding(bottom = 15.dp), text = "A product description is a form of marketing copy used to describe and explain the benefits of your product. In other words, it provides all the information and details of your product on your ecommerce site. These product details can be one sentence, a short paragraph or bulleted. They can be serious, funny or quirk", fontSize = 20, fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6, textColor = Color.Gray, textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold, lineHeight = 30, overflow = TextOverflow.Ellipsis)
            }
        }
}


