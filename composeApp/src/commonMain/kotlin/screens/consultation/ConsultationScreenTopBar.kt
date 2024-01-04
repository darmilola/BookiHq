package screens.consultation

import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansSemiBold
import Styles.Colors
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ImageComponent
import components.TextComponent
import kotlinx.coroutines.launch
import screens.main.MainViewModel
import widgets.PageBackNavWidget
import widgets.StepsProgressBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConsultationScreenTopBar(pagerState: PagerState) {

    val rowModifier = Modifier
        .fillMaxWidth()
        .height(60.dp)

    val stepList = arrayListOf<String>()
    stepList.add("Booking")
    stepList.add("Select Date")
    stepList.add("Payment")

    val colModifier = Modifier
        .padding(top = 40.dp, end = 0.dp)
        .fillMaxWidth()
        .height(140.dp)

    Column(modifier = colModifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 10.dp),
                contentAlignment = Alignment.CenterStart) {
                leftTopBarItem(pagerState)
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

            }

        }
        Row(modifier = Modifier.fillMaxWidth().height(80.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            StepsProgressBar(modifier = Modifier.fillMaxWidth().padding(start = 10.dp), numberOfSteps = stepList.size - 1, currentStep = pagerState.currentPage, stepItems = stepList)
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun leftTopBarItem(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    val navigator = LocalNavigator.currentOrThrow
    val currentPage = pagerState.currentPage
    PageBackNavWidget(){
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
}

@Composable
fun BookingTitle(){
        TextComponent(
            text = "Book Appointment",
            fontSize = 20,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
}


@Composable
fun rightTopBarItem() {}
