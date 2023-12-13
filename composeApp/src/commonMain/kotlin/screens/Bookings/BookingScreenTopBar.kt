package screens.Bookings

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansRegular
import GGSansSemiBold
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import components.ImageComponent
import components.TextComponent
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import kotlinx.coroutines.launch
import screens.authentication.AuthenticationComposeScreen
import screens.authentication.WelcomeScreen
import screens.main.MainViewModel
import screens.main.NotificationTab
import widgets.StepsProgressBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookingScreenTopBar(mainViewModel: MainViewModel, pagerState: PagerState) {

    val rowModifier = Modifier
        .fillMaxWidth()
        .height(40.dp)

    val colModifier = Modifier
        .padding(top = 40.dp, end = 0.dp)
        .fillMaxWidth()
        .height(120.dp)

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
                leftTopBarItem(mainViewModel, pagerState)
            }

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                BookingTitle()
            }

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth(0.20f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                rightTopBarItem()
            }

        }
        Row(modifier = rowModifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            val currentStep = remember { mutableStateOf(2) }
            StepsProgressBar(modifier = Modifier.fillMaxWidth().padding(start = 35.dp, end = 35.dp), numberOfSteps = 2, currentStep = currentStep.value)
        }
        stepItemTitle(pagerState)

    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun stepItemTitle(pagerState: PagerState) {
    val rowModifier = Modifier
        .fillMaxWidth()
        .height(20.dp)
    Row(modifier = rowModifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {

        TextComponent(
            text = "Select Service",
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            textModifier = Modifier
                .alpha(if (pagerState.currentPage == 0) 1F else 0F)
                .weight(1f)
                .padding(start = 10.dp)
        )

        TextComponent(
            text = "Select Specialist",
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Black,
            textModifier = Modifier.weight(1f).alpha(if (pagerState.currentPage == 1) 1F else 0F)
        )

        TextComponent(
            text = "Payment",
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.Black,
            textModifier = Modifier.weight(1f).padding(end = 10.dp).alpha(if (pagerState.currentPage == 2) 1F else 0F)
        )


    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun leftTopBarItem(mainViewModel: MainViewModel, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    val navigator = LocalNavigator.currentOrThrow
    val currentPage = pagerState.currentPage
    val modifier = Modifier
        .padding(start = 15.dp)
        .clickable {
                coroutineScope.launch {
                    when (currentPage) {
                        2 -> {
                            pagerState.animateScrollToPage(1)
                        }
                        1 -> {
                            pagerState.animateScrollToPage(0)
                        }
                        else -> {
                            navigator.pop()
                        }
                    }
                }
        }
        .size(22.dp)
    ImageComponent(imageModifier = modifier, imageRes = "back_arrow.png", colorFilter = ColorFilter.tint(color = Color.DarkGray))
}

@Composable
fun BookingTitle(){
    MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
        TextComponent(
                text = "Book Appointment",
                fontSize = 20,
                fontFamily = GGSansSemiBold,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
            )
    }
}

@Composable
fun rightTopBarItem() {
    val modifier = Modifier
        .padding(end = 10.dp)
        .background(color = Color.Transparent)
        .fillMaxWidth()
        .fillMaxHeight()

    val indicatorModifier = Modifier
        .padding(start = 10.dp, bottom = 25.dp, end = 4.dp)
        .background(color = Color.Transparent)
        .size(14.dp)
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(color = 0xFFFA2D65),
                    Color(color = 0xFFFA2D65)
                )
            ),
            shape = RoundedCornerShape(7.dp)
        )

    Box(modifier = modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        ImageComponent(imageModifier = Modifier.size(35.dp).clickable {
        }, imageRes = "clipboard_icon.png", colorFilter = ColorFilter.tint(color = Color.DarkGray))
        Box(modifier = indicatorModifier){}
    }
}

